package com.bookmoa.android.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.MainActivity
import com.bookmoa.android.R
import com.bookmoa.android.adapter.SearchBookAdapter
import com.bookmoa.android.databinding.FragmentSearchAuthorBinding
import com.bookmoa.android.models.SearchBookData
import com.bookmoa.android.models.SearchBookResponse
import com.bookmoa.android.services.AladinBookService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchAuthorFragment : Fragment() {

    private var _binding: FragmentSearchAuthorBinding? = null
    private val binding get() = _binding!!
    private lateinit var searchBookAdapter: SearchBookAdapter
    private lateinit var bookService: AladinBookService

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchAuthorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupBookServices()
    }

    private fun setupRecyclerView() {
        searchBookAdapter = SearchBookAdapter(itemClickedListener = { book ->
            (activity as? MainActivity)?.showBookDetail(book.isbn13)
        })
        binding.searchAuthorRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchBookAdapter
        }
    }

    private fun setupBookServices() {
        val retrofitForBooks = Retrofit.Builder()
            .baseUrl("https://www.aladin.co.kr/ttb/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        bookService = retrofitForBooks.create(AladinBookService::class.java)
    }

    fun searchBooksByName(query: String, queryType: String, callback: (List<SearchBookData>) -> Unit) {
        bookService.getBooksByName(getString(R.string.ApiKey), query, queryType)
            .enqueue(object : Callback<SearchBookResponse> {
                override fun onResponse(call: Call<SearchBookResponse>, response: Response<SearchBookResponse>) {
                    if (response.isSuccessful) {
                        val books = response.body()?.books?.map { book ->
                            book.copy(isbn13 = if (book.isbn13.isNotEmpty()) book.isbn13 else "0")
                        } ?: emptyList()
                        updateBookList(books)
                        callback(books)  // 데이터를 콜백으로 전달
                    } else {
                        Log.e("SearchAuthorFragment", "Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<SearchBookResponse>, t: Throwable) {
                    Log.e("SearchAuthorFragment", "Failure: ${t.message}")
                }
            })
    }

    fun updateBookList(books: List<SearchBookData>) {
        if (books.isNullOrEmpty()) {
            binding.searchAuthorAvailable.visibility = View.GONE
            binding.searchAuthorNotAvailable.visibility = View.VISIBLE
        } else {
            binding.searchAuthorAvailable.visibility = View.VISIBLE
            binding.searchAuthorNotAvailable.visibility = View.GONE
            searchBookAdapter.submitList(books)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}