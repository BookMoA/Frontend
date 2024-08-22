package com.bookmoa.android.study

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.MainActivity
import com.bookmoa.android.adapter.ListContentAdapter
import com.bookmoa.android.databinding.FragmentListContentBinding
import com.bookmoa.android.models.ListContentData
import com.bookmoa.android.services.RetrofitInstance
import com.bookmoa.android.services.TokenManager
import com.bumptech.glide.Glide

import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListContentFragment : Fragment() {

    lateinit var binding: FragmentListContentBinding
    private var itemListContentAdapter: ListContentAdapter? = null
    private lateinit var tokenManager: TokenManager

    companion object {
        private const val ARG_ID = "id"

        fun newInstance(id: Int): ListContentFragment {
            val fragment = ListContentFragment()
            val args = Bundle()
            args.putInt(ARG_ID, id)
            fragment.arguments = args
            return fragment
        }
    }

    private var item: ListContentData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val id = it.getInt(ARG_ID)
            tokenManager = TokenManager()


            Log.d("ListContentFragment", "Received id: $id")

            lifecycleScope.launch {
                val fetchedItem = fetchDataById(id)
                if (fetchedItem != null) {
                    item = fetchedItem
                    updateUI(item)
                }
            }
        }
    }

    private suspend fun fetchDataById(id: Int): ListContentData? {
        return try {
            val token = tokenManager.getToken()

            if (token != null) {
                Log.d("ListContentFragment", "Token found: $token")
                val response = RetrofitInstance.listcontentapi.getBookListById(id, "Bearer $token")

                if (response.isSuccessful) {
                    Log.d("ListContentFragment", "API call successful")
                    val apiResponse = response.body()
                    apiResponse?.data?.let { data ->
                        Log.d("ListContentFragment", "Data received: $data")
                        return ListContentData(
                            bookListId = data.bookListId,
                            img = data.img ?: "",
                            title = data.title,
                            spec = data.spec,
                            listStatus = data.listStatus,
                            nickname = data.nickname,
                            likeCnt = data.likeCnt,
                            bookCnt = data.bookCnt,
                            likeStatus = data.likeStatus,
                            books = data.books ?: emptyList() // 기본값 처리
                        ).also {
                            Log.d("ListContentFragment", "ListContentData created: $it")
                        }
                    }
                } else {
                    Log.e("ListContentFragment", "API call failed: ${response.errorBody()?.string()}")
                }
            } else {
                Log.e("ListContentFragment", "Token not found")
            }
            null
        } catch (e: Exception) {
            Log.e("ListContentFragment", "Network error", e)
            null
        }
    }
    fun postBookId(bookListId: Int, callback: (Boolean, String?) -> Unit) {
       val token = tokenManager.getToken()
        val call: Call<ResponseBody> = RetrofitInstance.postAnotherBookIdapi.postBookId(
            token = "Bearer $token",
            bookListId = bookListId
        )

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Log.d("ListContentFragment","post Success")
                    callback(true, response.body()?.string())
                } else {
                    // 실패 시 콜백 호출
                    Log.d("ListContentFragment","post miss${response.errorBody()?.string()}")
                    callback(false, response.errorBody()?.string())
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                // 요청 실패 시 콜백 호출
                callback(false, t.message)
            }
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListContentBinding.inflate(inflater, container, false)
        binding.listContentBackIcon.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

        itemListContentAdapter = ListContentAdapter()
        binding.listContentRvList.layoutManager = LinearLayoutManager(context)
        binding.listContentRvList.adapter = itemListContentAdapter

        binding.listContentStorageStoreIv.setOnClickListener{
            Log.d("test","${item!!.bookListId}")
            postBookId(item!!.bookListId) { success, response ->
                if (success) {
                    println("Success: $response")
                } else {
                    println("Failed: $response")
                }
            }
        }

        return binding.root
    }

    private fun updateUI(item: ListContentData?) {
        item?.let {
            Glide.with(this)
                .load(it.img) // 실제 이미지 URL 사용
                .into(binding.listContentImgIv)

            binding.listContentIntroduceTitleTv.text = it.title
            binding.listContentLikeNumTv.text = "${it.likeCnt}"
            binding.listContentNumTv.text = "총 ${it.bookCnt}권"
            binding.listContentIntroduceDetailTv.text=it.spec
            itemListContentAdapter?.updateItems(it.books)

        }
    }


}