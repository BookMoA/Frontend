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
import com.bookmoa.android.services.RetrofitInstance
import com.bookmoa.android.services.TokenManager
import com.bookmoa.android.adapter.ListDetailAdapter

import com.bookmoa.android.databinding.FragmentListDetailBinding
import com.bookmoa.android.models.ListContentData
import com.bookmoa.android.services.ApiService
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        return withContext(Dispatchers.IO) {
            try {
                val response = ApiService.createWithHeader(requireContext()).getBookListById(id)
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    apiResponse?.data?.let { data ->
                        return@withContext ListContentData(
                            bookListId = data.bookListId,
                            img = data.img ?: "",
                            title = data.title,
                            spec = data.spec,
                            listStatus = data.listStatus,
                            nickname = data.nickname,
                            likeCnt = data.likeCnt,
                            bookCnt = data.bookCnt,
                            likeStatus = data.likeStatus,
                            books = data.books ?: emptyList()
                        )
                    }
                } else {
                    // Handle unsuccessful response
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                // Handle exception
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "서버 통신 실패: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
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
            activity?.supportFragmentManager?.popBackStack()

        }
        binding.listDetailEditBtn.setOnClickListener {
            (activity as MainActivity).switchFragment(ListDetailEditFragment())
        }

        itemListDetailAdapter = ListDetailAdapter()

        binding.listDetailRv.layoutManager = LinearLayoutManager(context)

        binding.listDetailRv.adapter = itemListDetailAdapter



        return binding.root
    }

    private fun updateUI(item: ListContentData?) {
        item?.let {
            Glide.with(this)
                .load(it.img)
                .centerCrop()
                .into(binding.listDetailImgIv)

            binding.listDetailTitleTv.text = it.title
            binding.listDetailLikeTv.text = "${it.likeCnt}"
            binding.listDetailOwnerTv.text= it.nickname
            binding.listDetailIntroduce.text=it.spec
            itemListDetailAdapter?.updateItems(it.books)
        }
    }
}


