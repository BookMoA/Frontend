package com.bookmoa.android.search

import android.content.Intent
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
import com.bookmoa.android.home.RegisterbookActivity
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
    private var query: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchAuthorBinding.inflate(inflater, container, false)

        binding.searchAuthorBtn.setOnClickListener{
            val intent = Intent(requireContext(), RegisterbookActivity::class.java)

            startActivity(intent)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupBookServices()
    }

    private fun setupRecyclerView() {
        searchBookAdapter = SearchBookAdapter(
            itemClickedListener = { book ->
                (activity as? MainActivity)?.showBookDetail(book.isbn13)
            },
            query = query // Pass the query here
        )
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
        this.query = query // Update the query property
        bookService.getBooksByName(getString(R.string.ApiKey), query, queryType)
            .enqueue(object : Callback<SearchBookResponse> {
                override fun onResponse(call: Call<SearchBookResponse>, response: Response<SearchBookResponse>) {
                    if (response.isSuccessful) {
                        val books = response.body()?.books?.map { book ->
                            book.copy(isbn13 = if (book.isbn13.isNotEmpty()) book.isbn13 else "0")
                        } ?: emptyList()
                        updateBookList(books)
                        callback(books)  // 데이터 콜백
                    } else {
                        Log.e("SearchTitleFragment", "Error: ${response.errorBody()?.string()}")
                        callback(emptyList())  // 오류 발생 시 빈 리스트
                    }
                }

                override fun onFailure(call: Call<SearchBookResponse>, t: Throwable) {
                    Log.e("SearchTitleFragment", "Failure: ${t.message}")
                    callback(emptyList())  // 실패 시 빈 리스트
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
            searchBookAdapter.submitList(books) // Update adapter with new list
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}