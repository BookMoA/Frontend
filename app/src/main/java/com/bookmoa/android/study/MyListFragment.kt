package com.bookmoa.android.study

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.MainActivity
import com.bookmoa.android.R
import com.bookmoa.android.services.RetrofitInstance
import com.bookmoa.android.services.TokenManager
import com.bookmoa.android.adapter.StorageListAdapter
import com.bookmoa.android.databinding.FragmentMyListBinding
import com.bookmoa.android.models.StorageListData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyListFragment : Fragment() {
    lateinit var binding: FragmentMyListBinding
    private var storageRVAdapter: StorageListAdapter? = null
    private lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyListBinding.inflate(inflater, container, false)
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

        loadMyListData()


    }

    private fun loadMyListData() {
        val token = tokenManager.getToken()
        if (token != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    // API 호출
                    val response = RetrofitInstance.storageListapi.getStorageList("Bearer $token")

                    withContext(Dispatchers.Main) {
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
                                Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            // 응답이 실패한 경우
                            Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        } else {
            handleNoToken()
        }
    }

    private fun handleNoToken() {
        Toast.makeText(context, "로그인이 필요합니다. 로그인 화면으로 이동합니다.", Toast.LENGTH_SHORT).show()
        (activity as MainActivity).switchFragment(StudyFragment())
    }



}