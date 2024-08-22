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
import com.bookmoa.android.adapter.StorageListAddAdapter
import com.bookmoa.android.databinding.FragmentMyListStorageBinding
import com.bookmoa.android.models.StorageListData
import com.bookmoa.android.models.StorageListResponse
import com.bookmoa.android.services.ApiService
import com.bookmoa.android.services.BooksRequest
import com.bookmoa.android.services.RetrofitInstance
import com.bookmoa.android.services.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyListStorageFragment : Fragment() {

    lateinit var binding: FragmentMyListStorageBinding
    private var storageRVAdapter: StorageListAddAdapter? = null
    private lateinit var tokenManager: TokenManager
    private var selectedBookIds: MutableList<Int> = mutableListOf() // 선택된 ID 리스트
    private var selectedId: Int? =null

    private lateinit var api: ApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyListStorageBinding.inflate(inflater, container, false)

        tokenManager = TokenManager()

        storageRVAdapter = StorageListAddAdapter()
        binding.myListRv.layoutManager = LinearLayoutManager(context)
        binding.myListRv.adapter = storageRVAdapter

        storageRVAdapter?.setOnItemClickListener(object : StorageListAddAdapter.OnItemClickListener {
            override fun onItemClick(view: View, position: Int, data: StorageListData) {
                if (selectedId == data.bookListId) {
                    selectedId = null // 이미 선택된 항목이면 해제
                } else {
                    selectedId = data.bookListId // 새 항목 선택
                }
                storageRVAdapter?.notifyItemChanged(position) // 변경된 항목만 갱신
            }
        })

        val receivedIds = arguments?.getIntegerArrayList("selected_ids") ?: arrayListOf()
        selectedBookIds.addAll(receivedIds) // 이전 프래그먼트에서 넘어온 선택된 ID들 추가
        Log.d("MyListStorageFragment", "Received selected IDs: $selectedBookIds")

        binding.myListStorageSubmitBtn.setOnClickListener {
            postBookIds(selectedId!!, selectedBookIds) { success, response ->
                if (success) {
                    Toast.makeText(context, "POST request successful!", Toast.LENGTH_SHORT).show()
                    activity?.supportFragmentManager?.popBackStack()
                } else {
                    Toast.makeText(context, "POST request failed: $response", Toast.LENGTH_SHORT).show()
                }
            }
        }

        loadMyListData()

        return binding.root
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
                        if (apiResponse != null) {
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
                            Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
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


        /*
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
                            if (apiResponse != null) {
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

         */
    }

    private fun handleNoToken() {
        Toast.makeText(context, "로그인이 필요합니다. 로그인 화면으로 이동합니다.", Toast.LENGTH_SHORT).show()
        (activity as MainActivity).switchFragment(StudyFragment())
    }

    private fun postBookIds(bookListId: Int, ids: List<Int>, callback: (Boolean, String?) -> Unit) {
        val token = tokenManager.getToken()
        val request = BooksRequest(booksId = ids)
        val call: Call<ResponseBody> = RetrofitInstance.postAnotherBookselectapi.postBookIds(
            token = "Bearer $token",
            bookListId = bookListId,
            request
        )

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("MyListStorageFragment", "POST Success: ${response.body()?.string()}")
                    callback(true, response.body()?.string())
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.d("MyListStorageFragment", "POST failed: $errorBody")
                    callback(false, errorBody)
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.d("MyListStorageFragment", "POST request failed: ${t.message}")
                callback(false, t.message)
            }
        })
    }
}