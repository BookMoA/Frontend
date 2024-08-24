package com.bookmoa.android.study

import BookDetail
import BookDetailResponse
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bookmoa.android.R
import com.bookmoa.android.databinding.FragmentBookResultBinding
import com.bookmoa.android.databinding.FragmentRecommendBinding
import com.bookmoa.android.models.BookEntryResponse
import com.bookmoa.android.models.NewBookAladin
import com.bookmoa.android.services.AladinBookDetailService
import com.bookmoa.android.services.ApiService
import com.bookmoa.android.services.BookDBCheckService
import com.bookmoa.android.services.BookEntryService
import com.bookmoa.android.services.TokenManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BookResultFragment : Fragment() {

    private var _binding: FragmentBookResultBinding? = null
    private val binding get() = _binding!!
    private lateinit var api: ApiService
    private lateinit var bookDetailService: AladinBookDetailService
    private lateinit var bookCheckService: BookDBCheckService
    private lateinit var bookEntryService: BookEntryService
    private lateinit var tokenManager: TokenManager
    private var isbn13: String? = null
    private var coverImg: String? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBookResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isbn13 = arguments?.getString("isbn13")
        setupBookService()
        isbn13?.let { fetchBookDetails(it) }

        binding.bookResultAddBookBtn.setOnClickListener {
            isbn13?.let { isbn ->
                lifecycleScope.launch {
                    val bookId = checkBookExistence(isbn)
                    if (bookId != null) {
                        goToNextFragment(bookId.toInt())
                    } else  {
                        val newBookId = createNewBook(
                            NewBookAladin(
                            title = binding.bookResultBookTitleTv.text.toString(),
                            writer = binding.bookResultBookAuthorTv.text.toString(),
                            //description = binding.recommendBookIntroduceContentTv.text.toString(),
                            publisher = binding.bookResultBookPublisherContentTv.text.toString(),
                            isbn = isbn,
                            page = binding.bookResultBookPageContentTv.text.toString().replace("p", "").toInt(),
                            coverImage = coverImg!!
                        )
                        )
                        newBookId?.let { goToNextFragment(it.toInt()) }
                    }
                }
            }
        }
    }

    private fun setupBookService() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.aladin.co.kr/ttb/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        bookDetailService = retrofit.create(AladinBookDetailService::class.java)
        bookCheckService = retrofit.create(BookDBCheckService::class.java)
        bookEntryService = retrofit.create(BookEntryService::class.java)
        tokenManager = TokenManager()
    }

    private fun fetchBookDetails(isbn13: String) {
        val apiKey = getString(R.string.ApiKey)
        Log.d("RecommendFragment", "Fetching details for ISBN13: $isbn13")

        bookDetailService.getBooksByISBN(apiKey, isbn13).enqueue(object :
            Callback<BookDetailResponse> {
            override fun onResponse(call: Call<BookDetailResponse>, response: Response<BookDetailResponse>) {
                if (response.isSuccessful) {
                    val bookDetail = response.body()!!.items
                    if (bookDetail != null) {
                        Log.d("RecommendFragment", "Book details fetched successfully: $bookDetail")
                        renderView(bookDetail.get(0))
                    } else {
                        Log.e("RecommendFragment", "No book details found in the response")
                    }
                } else {
                    Log.e("RecommendFragment", "API Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<BookDetailResponse>, t: Throwable) {
                Log.e("RecommendFragment", "API Failure: ${t.message}")
            }
        })
    }

    private fun renderView(bookDetail: BookDetail) {
        Log.d("RecommendFragment", "Rendering book details: $bookDetail")

        binding.bookResultBookTitleTv.text = bookDetail.title
        binding.bookResultBookAuthorTv.text = bookDetail.author
        binding.bookResultBookPublisherContentTv.text = bookDetail.publisher
        Log.d("RecommendFragment", "${bookDetail.description}")
        if (bookDetail.description.isNullOrEmpty()) {
            binding.bookResultBookIntroduceContentTv.text = "등록된 책 소개가 없습니다."
        } else {
            binding.bookResultBookIntroduceContentTv.text = bookDetail.description
        }


        binding.bookResultBookISBNContentTv.text = bookDetail.isbn13
        binding.bookResultBookPageContentTv.text = "${bookDetail.subInfo?.itemPage}p"

        coverImg = bookDetail.img
        Glide.with(binding.bookResultBookImg.context)
            .load(bookDetail.img)
            .into(binding.bookResultBookImg)
    }

    private suspend fun checkBookExistence(isbn13: String): Long? {
        return withContext(Dispatchers.IO) {
            try {
                api = ApiService.createWithHeader(requireContext())
                val response = api.checkBook(isbn13)
                if (response.isSuccessful) {
                    val bookCheckResponse = response.body()
                    if (bookCheckResponse?.result == true) {
                        bookCheckResponse.data?.bookId
                    } else {
                        null
                    }
                } else {
                    null
                }
            } catch (e: Exception) {
                null
            }
        }
    }
    private suspend fun createNewBook(newBook: NewBookAladin): Long? {
        return withContext(Dispatchers.IO) {
            try {
                // ApiService 인스턴스 생성
                api = ApiService.createWithHeader(requireContext())

                // API 호출
                val response: Response<BookEntryResponse> = api.createBook(newBook)

                if (response.isSuccessful) {
                    val bookCreationResponse = response.body()
                    val bookId = bookCreationResponse?.data?.bookId
                    Log.d("RecommendFragment", "New book created with ID: $bookId")
                    bookId
                } else {
                    Log.e(
                        "RecommendFragment",
                        "Failed to create new book: ${response.errorBody()?.string()}"
                    )
                    null
                }
            } catch (e: Exception) {
                Log.e("RecommendFragment", "Network error", e)
                null
            }
        }
    }
    private fun goToNextFragment(bookId: Int) {
        val bundle = Bundle().apply {
            putIntegerArrayList("selected_ids",  ArrayList(listOf(bookId)))
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