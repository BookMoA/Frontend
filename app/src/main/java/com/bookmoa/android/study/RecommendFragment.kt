package com.bookmoa.android.study

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bookmoa.android.R
import com.bookmoa.android.databinding.FragmentRecommendBinding
import com.bookmoa.android.models.RecommendBookDetailData
import com.bookmoa.android.models.RecommendBookDetailResponse
import com.bookmoa.android.services.AladinBookDetailService
import com.bookmoa.android.services.ApiService
import com.bookmoa.android.services.BookDBCheckService
import com.bookmoa.android.services.BookEntryService
import com.bookmoa.android.services.TokenManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RecommendFragment : Fragment() {

    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!
    private lateinit var api: ApiService
    private lateinit var bookDetailService: AladinBookDetailService
    private lateinit var bookCheckService: BookDBCheckService
    private lateinit var bookEntryService: BookEntryService
    private lateinit var tokenManager: TokenManager
    private var bookId: Int? = null
    private var coverImg: String? = null

    companion object {
        private const val ARG_BOOK_ID = "book_id"

        fun newInstance(bookId: Int): RecommendFragment {
            val fragment = RecommendFragment()
            val args = Bundle().apply {
                putInt(ARG_BOOK_ID, bookId)  // bookId를 Int로 저장
            }
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Fetch the book ID from the arguments
        arguments?.let {
            bookId = it.getInt(ARG_BOOK_ID, -1)  // 기본값으로 -1을 사용
        }

        if (bookId != null && bookId != -1) {
            fetchBookDetails(bookId!!)  // BookId가 유효한 경우에만 호출
        } else {
            Toast.makeText(context, "Book ID가 유효하지 않습니다.", Toast.LENGTH_SHORT).show()
        }

        binding.recommendAddBookBtn.setOnClickListener {
            if (bookId != null) {
                goToNextFragment(bookId!!)
            }
        }
    }

    private fun fetchBookDetails(bookId: Int) {
        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())

            // Call the API with the book ID
            api.RecommendBookDetail(bookId).enqueue(object : Callback<RecommendBookDetailResponse> {
                override fun onResponse(
                    call: Call<RecommendBookDetailResponse>,
                    response: Response<RecommendBookDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        val bookDetail = response.body()?.data
                        if (bookDetail != null) {
                            Log.d(
                                "RecommendFragment",
                                "Book details fetched successfully: $bookDetail"
                            )
                            renderView(bookDetail)
                        } else {
                            Log.e("RecommendFragment", "No book details found in the response")
                        }
                    } else {
                        Log.e(
                            "RecommendFragment",
                            "API Error: ${response.errorBody()?.string()}"
                        )
                    }
                }

                override fun onFailure(call: Call<RecommendBookDetailResponse>, t: Throwable) {
                    Log.e("RecommendFragment", "API Failure: ${t.message}")
                }
            })
        }
    }

    private fun renderView(bookDetail: RecommendBookDetailData) {
        Log.d("RecommendFragment", "Rendering book details: $bookDetail")

        binding.recommendBookTitleTv.text = bookDetail.title
        binding.recommendBookAuthorTv.text = bookDetail.writer
        binding.recommendBookPublisherContentTv.text = bookDetail.publisher
        Log.d("RecommendFragment", "${bookDetail.description}")
        binding.recommendBookIntroduceContentTv.text = bookDetail.description
            ?: "등록된 책 소개가 없습니다."

        binding.recommendBookISBNContentTv.text = bookDetail.isbn
        binding.recommendBookPageContentTv.text = bookDetail.page.toString()

        coverImg = bookDetail.coverImage
        Glide.with(binding.recommendBookImg.context)
            .load(bookDetail.coverImage)
            .into(binding.recommendBookImg)
    }

    private fun goToNextFragment(bookId: Int) {
        val bundle = Bundle().apply {
            putIntegerArrayList("selected_ids", ArrayList(listOf(bookId)))
        }

        val nextFragment = MyListStorageFragment()
        nextFragment.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, nextFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}