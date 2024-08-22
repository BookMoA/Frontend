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
import com.bookmoa.android.databinding.FragmentFinishedBooksBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FinishedBooksFragment : Fragment() {

    private var _binding: FragmentFinishedBooksBinding? = null
    private val binding get() = _binding!!
    private var storageRVAdapter: StorageBookAdapter? = null
    private lateinit var tokenManager: TokenManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFinishedBooksBinding.inflate(inflater, container, false)

        // 어댑터 초기화
        storageRVAdapter = StorageBookAdapter()

        // RecyclerView에 레이아웃 매니저 설정
        binding.finishedBooksRvList.layoutManager = GridLayoutManager(context, 3)

        // RecyclerView에 어댑터 설정
        binding.finishedBooksRvList.adapter = storageRVAdapter

        tokenManager = TokenManager()

        loadBookData()

        return binding.root
    }

    private fun loadBookData() {
        val token = tokenManager.getToken()
        if (token != null) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitInstance.storageBookapi.getBooks("Bearer $token", "finished")
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
    }

    private fun handleNoToken() {
        Toast.makeText(context, "로그인이 필요합니다. 로그인 화면으로 이동합니다.", Toast.LENGTH_SHORT).show()
        (activity as MainActivity).switchFragment(StudyFragment())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}