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
import com.bookmoa.android.adapter.StorageListEditAdapter
import com.bookmoa.android.databinding.FragmentMyListEditBinding
import com.bookmoa.android.models.StorageListResponse
import com.bookmoa.android.services.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyListEditFragment: Fragment() {
    lateinit var binding: FragmentMyListEditBinding
    private var storageRVAdapter: StorageListEditAdapter? = null
    private lateinit var api: ApiService
    private var listener: OnEditButtonClickListener? = null
    private val selectedIds = mutableSetOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyListEditBinding.inflate(inflater, container, false)
        // 임의의 데이터를 어댑터에 추가
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        storageRVAdapter = StorageListEditAdapter(selectedIds)
        binding.myListEditRv.layoutManager = LinearLayoutManager(context)
        binding.myListEditRv.adapter = storageRVAdapter


        binding.myListEditDelete.setOnClickListener {
            deletBookIds(selectedIds.toList()) { success, response ->
                if (success) {
                    Toast.makeText(context, "삭제가 완료되었습니다", Toast.LENGTH_SHORT).show()
                    activity?.supportFragmentManager?.popBackStack()
                } else {
                    Toast.makeText(context, "POST request failed: $response", Toast.LENGTH_SHORT).show()
                    Log.d("MyListFragment","$response")
                }
            }
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
                                Log.d("SelectedIds", "Current selected IDs: ${selectedIds.joinToString()}")
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
    private fun deletBookIds(ids: List<Int>, callback: (Boolean, String?) -> Unit) {
        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())
            api.deleteBookListId(ids).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        Log.d("My", "DELETE Success: ${response.body()?.string()}")
                        callback(true, response.body()?.string())
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.d("MyListStorageFragment", "DELETE failed: $errorBody")
                        callback(false, errorBody)
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("MyListStorageFragment", "DELETE request failed: ${t.message}")
                    callback(false, t.message)
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