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
import com.bookmoa.android.databinding.FragmentRecommendBinding
import com.bookmoa.android.models.NewBookAladin
import com.bookmoa.android.services.AladinBookDetailService
import com.bookmoa.android.services.BookDBCheckService
import com.bookmoa.android.services.BookEntryService
import com.bookmoa.android.services.RetrofitInstance
import com.bookmoa.android.services.TokenManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL


class RecommendFragment : Fragment() {

    private var _binding: FragmentRecommendBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookDetailService: AladinBookDetailService
    private lateinit var bookCheckService: BookDBCheckService
    private lateinit var bookEntryService: BookEntryService
    private lateinit var tokenManager: TokenManager
    private var isbn13: String? = null
    private var coverImg: String? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecommendBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isbn13 = arguments?.getString("isbn13")
        setupBookService()
        isbn13?.let { fetchBookDetails(it) }

        binding.recommendAddBookBtn.setOnClickListener {
            isbn13?.let { isbn ->
                lifecycleScope.launch {
                    val bookId = checkBookExistence(isbn)
                    if (bookId != null) {
                        goToNextFragment(bookId)
                    } else {
                        val newBookId = createNewBook(NewBookAladin(
                            title = binding.recommendBookTitleTv.text.toString(),
                            writer = binding.recommendBookAuthorTv.text.toString(),
                            //description = binding.recommendBookIntroduceContentTv.text.toString(),
                            publisher = binding.recommendBookPublisherContentTv.text.toString(),
                            isbn = isbn,
                            page = binding.recommendBookPageContentTv.text.toString().replace("p", "").toInt(),
                            coverImage = coverImg!!
                        ))
                        newBookId?.let { goToNextFragment(it) }
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

        bookDetailService.getBooksByISBN(apiKey, isbn13).enqueue(object : Callback<BookDetailResponse> {
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

        binding.recommendBookTitleTv.text = bookDetail.title
        binding.recommendBookAuthorTv.text = bookDetail.author
        binding.recommendBookPublisherContentTv.text = bookDetail.publisher
        Log.d("RecommendFragment", "${bookDetail.description}")
        if (bookDetail.description.isNullOrEmpty()) {
            binding.recommendBookIntroduceContentTv.text = "등록된 책 소개가 없습니다."
        } else {
            binding.recommendBookIntroduceContentTv.text = bookDetail.description
        }


        binding.recommendBookISBNContentTv.text = bookDetail.isbn13
        binding.recommendBookPageContentTv.text = "${bookDetail.subInfo?.itemPage}p"

        coverImg = bookDetail.img
        Glide.with(binding.recommendBookImg.context)
            .load(bookDetail.img)
            .into(binding.recommendBookImg)
    }

    private suspend fun checkBookExistence(isbn13: String): Long? {
        val token = tokenManager.getToken()
        return try {
            if (token != null) {
                val response = RetrofitInstance.bookCheckApi.checkBook("Bearer $token", isbn13)
                if (response.isSuccessful) {
                    val bookCheckResponse = response.body()
                    if (bookCheckResponse?.result == true) {
                        Log.d("RecommendFragment", "Book exists in DB with ID: ${bookCheckResponse.data?.bookId}")
                        return bookCheckResponse.data?.bookId
                    } else {
                        Log.d("RecommendFragment", "Book does not exist in DB")
                        null
                    }
                } else {
                    Log.e("RecommendFragment", "Failed to check book existence: ${response.errorBody()?.string()}")
                    null
                }
            } else {
                Log.e("RecommendFragment", "Token not found")
                null
            }
        } catch (e: Exception) {
            Log.e("RecommendFragment", "Network error", e)
            null
        }
    }

    fun downloadImage(url: String, outputFile: File): Boolean {
        return try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.apply {
                requestMethod = "GET"
                connectTimeout = 10000 // 10 seconds
                readTimeout = 10000 // 10 seconds
            }

            connection.inputStream.use { input ->
                FileOutputStream(outputFile).use { output ->
                    input.copyTo(output)
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    private suspend fun createNewBook(newBook: NewBookAladin): Long? {
        val token = tokenManager.getToken()

        return try {  // Add return here
            if (token != null) {
                val response = RetrofitInstance.bookEntryApi.createBook("Bearer $token", newBook) // Add newBook as argument
                if (response.isSuccessful) {
                    Log.d(
                        "RecommendFragment",
                        "New book created with ID: ${response.body()?.data?.bookId}"
                    )
                    response.body()?.data?.bookId
                } else {
                    Log.e(
                        "RecommendFragment",
                        "Failed to create new book: ${response.errorBody()?.string()}"
                    )
                    null
                }
            } else {
                Log.e("RecommendFragment", "Token not found")
                null
            }
        } catch (e: Exception) {
            Log.e("RecommendFragment", "Network error", e)
            null
        }
    }
    private fun goToNextFragment(bookId: Long) {
        val bundle = Bundle().apply {
            putLong("bookId", bookId)
        }

//        val nextFragment = NextFragment()
//        nextFragment.arguments = bundle
//
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(R.id.main_frm, nextFragment)
//            .addToBackStack(null)
//            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}