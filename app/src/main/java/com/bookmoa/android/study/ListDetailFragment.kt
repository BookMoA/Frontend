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
import com.bookmoa.android.adapter.ListDetailAdapter
import com.bookmoa.android.databinding.FragmentListContentBinding

import com.bookmoa.android.databinding.FragmentListDetailBinding
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class ListDetailFragment : Fragment() {
    lateinit var binding: FragmentListDetailBinding
    private var itemListDetailAdapter: ListDetailAdapter? = null
    private lateinit var tokenManager: TokenManager

    companion object {
        private const val ARG_ID = "id"

        fun newInstance(id: Int): ListDetailFragment {
            val fragment = ListDetailFragment()
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
                    Log.e(
                        "ListContentFragment",
                        "API call failed: ${response.errorBody()?.string()}"
                    )
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
        binding = FragmentListDetailBinding.inflate(inflater, container, false)

        binding.listDetailBackIcon.setOnClickListener {
            (activity as MainActivity).switchFragment(StudyFragment())

        }
        binding.listDetailEditBtn.setOnClickListener {
            (activity as MainActivity).switchFragment(ListDetailEditFragment())
        }

        itemListDetailAdapter = ListDetailAdapter()

        // RecyclerView에 레이아웃 매니저 설정
        binding.listDetailRv.layoutManager = LinearLayoutManager(context)

        // RecyclerView에 어댑터 설정
        binding.listDetailRv.adapter = itemListDetailAdapter



        return binding.root
    }

    private fun updateUI(item: ListContentData?) {
        item?.let {
            Glide.with(this)
                .load(it.img) // 실제 이미지 URL 사용
                .into(binding.listDetailImgIv)

            binding.listDetailTitleTv.text = it.title
            binding.listDetailLikeTv.text = "${it.likeCnt}"
            itemListDetailAdapter?.updateItems(it.books)
        }
    }
}


