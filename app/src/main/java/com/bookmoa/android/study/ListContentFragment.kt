package com.bookmoa.android.study

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.MainActivity
import com.bookmoa.android.R
import com.bookmoa.android.RetrofitInstance
import com.bookmoa.android.TokenManager
import com.bookmoa.android.adapter.ListContentAdapter
import com.bookmoa.android.databinding.FragmentListContentBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.launch

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
            tokenManager = TokenManager(requireContext())

            // 로그 출력 (여기서 id 값을 확인)
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListContentBinding.inflate(inflater, container, false)
        binding.listContentBackIcon.setOnClickListener {
            (activity as MainActivity).switchFragment(StudyFragment())
        }

        // 임시로 토큰 설정 (여기에서 직접 토큰을 설정)

        itemListContentAdapter = ListContentAdapter()
        binding.listContentRvList.layoutManager = LinearLayoutManager(context)
        binding.listContentRvList.adapter = itemListContentAdapter

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
        }
    }


}