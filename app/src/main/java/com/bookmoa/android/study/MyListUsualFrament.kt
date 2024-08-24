package com.bookmoa.android.study

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.R
import com.bookmoa.android.adapter.StorageListAdapter
import com.bookmoa.android.databinding.FragmentMyListUsualBinding
import com.bookmoa.android.models.StorageListData
import com.bookmoa.android.models.StorageListResponse
import com.bookmoa.android.services.ApiService
import com.bookmoa.android.services.TokenManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyListUsualFrament: Fragment() {
    lateinit var binding: FragmentMyListUsualBinding
    private var storageRVAdapter: StorageListAdapter? = null
    private lateinit var tokenManager: TokenManager
    private lateinit var api: ApiService
    private var listener: OnEditButtonClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyListUsualBinding.inflate(inflater, container, false)
        // 임의의 데이터를 어댑터에 추가
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tokenManager = TokenManager()

        storageRVAdapter = StorageListAdapter()
        binding.myListRv.layoutManager = LinearLayoutManager(context)
        binding.myListRv.adapter = storageRVAdapter

        storageRVAdapter?.setOnItemClickListener(object : StorageListAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, data: StorageListData) {
                val fragment = ListDetailFragment.newInstance(data.bookListId) // 전환할 Fragment
                Log.d("FragmentTransaction", "Navigating to ListDetailFragment with ID: ${data.bookListId}")

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        })
        binding.wantToReadEdit.setOnClickListener {
            listener?.onEditButtonClick()
        }
        loadMyListData()


    }

    private fun loadMyListData() {

        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())
            api.getStorageList().enqueue(object: Callback<StorageListResponse> {
                override fun onResponse(
                    call: Call<StorageListResponse>,
                    response: Response<StorageListResponse>
                ) {
                    if (response.isSuccessful) {
                        // 응답이 성공적일 경우
                        val apiResponse = response.body()
                        if (apiResponse != null ) {
                            val list = apiResponse.data
                            if (list != null) {
                                storageRVAdapter?.updateItems(list)
                            } else {
                                // 데이터가 없는 경우
                                Toast.makeText(context, "데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // 응답이 성공적이지만 `result`가 false인 경우
                            Log.d("[LIST]", "${response.errorBody()?.string()}")
                            Toast.makeText(context, "", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // 응답이 실패한 경우
                        Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<StorageListResponse>, t: Throwable) {
                    Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (parentFragment is OnEditButtonClickListener) {
            listener = parentFragment as OnEditButtonClickListener
        } else {
            throw RuntimeException("$context must implement OnEditButtonClickListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}