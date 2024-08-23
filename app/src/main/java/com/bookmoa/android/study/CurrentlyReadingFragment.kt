package com.bookmoa.android.study

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bookmoa.android.MainActivity
import com.bookmoa.android.services.RetrofitInstance
import com.bookmoa.android.services.TokenManager
import com.bookmoa.android.adapter.StorageBookAdapter
import com.bookmoa.android.databinding.FragmentCurrentlyReadingBinding
import com.bookmoa.android.models.SearchBookData
import com.bookmoa.android.models.StorageBookData
import com.bookmoa.android.models.StorageBookResponse
import com.bookmoa.android.models.StorageListData
import com.bookmoa.android.services.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CurrentlyReadingFragment : Fragment() {

    private var _binding: FragmentCurrentlyReadingBinding? = null
    private val binding get() = _binding!!
    private var storageRVAdapter: StorageBookAdapter? = null
    private lateinit var tokenManager: TokenManager

    private lateinit var api: ApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCurrentlyReadingBinding.inflate(inflater, container, false)

        // 어댑터 초기화
        storageRVAdapter = StorageBookAdapter()

        // RecyclerView에 레이아웃 매니저 설정
        binding.readingBooksRvList.layoutManager = GridLayoutManager(context, 3)

        // RecyclerView에 어댑터 설정
        binding.readingBooksRvList.adapter = storageRVAdapter

        tokenManager = TokenManager()

        loadBookData()
        return binding.root
    }


    private fun loadBookData() {

        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())

            api.getBooks("reading").enqueue(object: Callback<StorageBookResponse> {
                override fun onResponse(
                    call: Call<StorageBookResponse>,
                    response: Response<StorageBookResponse>
                ) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        if (apiResponse != null && apiResponse.result) {
                            val books= apiResponse.data?.books
                            if (books != null) {
                               updateBookList(books)
                            } else {
                                Toast.makeText(context, "데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                        Log.e("API Error", "Response code: ${response.code()}, message: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<StorageBookResponse>, t: Throwable) {
                    Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    Log.d("Network Error", "Exception during API call")
                }

            })
            /*
            try {
                val response = RetrofitInstance.storageBookapi.getBooks("reading")
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        if (apiResponse != null && apiResponse.result) {
                            val books= apiResponse.data?.books
                            if (books != null) {
                                storageRVAdapter?.updateItems(books)
                            } else {
                                Toast.makeText(context, "데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                        Log.e("API Error", "Response code: ${response.code()}, message: ${response.message()}")
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    Log.e("Network Error", "Exception during API call", e)
                }
            }

             */
        }

        /*
        val token = tokenManager.getToken()
        if (token != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitInstance.storageBookapi.getBooks("Bearer $token", "reading")
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val apiResponse = response.body()
                            if (apiResponse != null && apiResponse.result) {
                                val books= apiResponse.data?.books
                                if (books != null) {
                                    storageRVAdapter?.updateItems(books)
                                } else {
                                    Toast.makeText(context, "데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                            Log.e("API Error", "Response code: ${response.code()}, message: ${response.message()}")
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                        Log.e("Network Error", "Exception during API call", e)
                    }
                }
            }
        } else {
            handleNoToken()
        }

         */
    }

    fun updateBookList(books: List<StorageBookData>) {
        if (books.isNullOrEmpty()) {
            binding.readingBooksRvList.visibility = View.GONE
            binding.readingBooksNotAvailable.visibility = View.VISIBLE
        } else {
            binding.readingBooksRvList.visibility = View.VISIBLE
            binding.readingBooksNotAvailable.visibility = View.GONE
          storageRVAdapter?.updateItems(books)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
