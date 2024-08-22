package com.bookmoa.android.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import com.bookmoa.android.R
import com.bookmoa.android.services.TokenManager
import com.bookmoa.android.adapter.SearchVpAdapter
import com.bookmoa.android.databinding.FragmentSearchBinding
import com.bookmoa.android.models.SearchBookListResponse
import com.bookmoa.android.models.SearchBookResponse
import com.bookmoa.android.models.SearchMemoResponse
import com.bookmoa.android.services.AladinBookService
import com.bookmoa.android.services.RetrofitInstance
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookService: AladinBookService
    private lateinit var viewPagerAdapter: SearchVpAdapter
    private lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        tokenManager = TokenManager()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        setupBookServices()
        initSearchView()
    }

    private fun setupViewPager() {
        viewPagerAdapter = SearchVpAdapter(this)
        binding.searchContentVp.adapter = viewPagerAdapter

        TabLayoutMediator(binding.searchContentTb, binding.searchContentVp) { tab, position ->
            tab.text = when (position) {
                0 -> "전체"
                1 -> "제목"
                2 -> "지은이"
                3 -> "북 리스트"
                4 -> "독서 메모"
                else -> "Unknown"
            }
        }.attach()
    }

    private fun setupBookServices() {
        val retrofitForBooks = Retrofit.Builder()
            .baseUrl("https://www.aladin.co.kr/ttb/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        bookService = retrofitForBooks.create(AladinBookService::class.java)
    }

    private fun initSearchView() {
        binding.searchSv.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    Log.d("SearchFragment", "Search query submitted: $query")
                    searchBooks(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun searchBooks(query: String) {
        val token = tokenManager.getToken()
        Log.d("SearchFragment", "Searching with query: $query")

        val fragments = listOf(
            childFragmentManager.findFragmentByTag("f0") as? SearchTotalFragment,
            childFragmentManager.findFragmentByTag("f1") as? SearchTitleFragment,
            childFragmentManager.findFragmentByTag("f2") as? SearchAuthorFragment,
            childFragmentManager.findFragmentByTag("f3") as? SearchBookListFragment,
            childFragmentManager.findFragmentByTag("f4") as? SearchMemoFragment
        )

        fragments.forEach { fragment ->
            when (fragment) {
                is SearchTitleFragment -> {
                    Log.d("SearchFragment", "Searching titles...")
                    bookService.getBooksByName(getString(R.string.ApiKey), query, queryType = "Title")
                        .enqueue(object : Callback<SearchBookResponse> {
                            override fun onResponse(call: Call<SearchBookResponse>, response: Response<SearchBookResponse>) {
                                Log.d("SearchFragment", "Title search response: ${response.code()}")
                                if (response.isSuccessful) {
                                    Log.d("SearchFragment", "Title search successful")
                                    val books = response.body()?.books?.map { book ->
                                        book.copy(isbn13 = if (book.isbn13.isNotEmpty()) book.isbn13 else "0")
                                    }
                                    fragment.updateBookList(books ?: emptyList())
                                } else {
                                    Log.e("SearchFragment", "Title search API Error: ${response.errorBody()?.string()}")
                                }
                            }

                            override fun onFailure(call: Call<SearchBookResponse>, t: Throwable) {
                                Log.e("SearchFragment", "Title search API Failure: ${t.message}")
                            }
                        })
                }
                is SearchAuthorFragment -> {
                    Log.d("SearchFragment", "Searching authors...")
                    bookService.getBooksByName(getString(R.string.ApiKey), query, queryType = "Author")
                        .enqueue(object : Callback<SearchBookResponse> {
                            override fun onResponse(call: Call<SearchBookResponse>, response: Response<SearchBookResponse>) {
                                Log.d("SearchFragment", "Author search response: ${response.code()}")
                                if (response.isSuccessful) {
                                    Log.d("SearchFragment", "Author search successful")
                                    val books = response.body()?.books?.map { book ->
                                        book.copy(isbn13 = if (book.isbn13.isNotEmpty()) book.isbn13 else "0")
                                    }
                                    fragment.updateBookList(books ?: emptyList())
                                } else {
                                    Log.e("SearchFragment", "Author search API Error: ${response.errorBody()?.string()}")
                                }
                            }

                            override fun onFailure(call: Call<SearchBookResponse>, t: Throwable) {
                                Log.e("SearchFragment", "Author search API Failure: ${t.message}")
                            }
                        })
                }
                is SearchBookListFragment -> {
                    Log.d("SearchFragment", "Searching book lists...")
                    RetrofitInstance.searchListApi.getBookList("Bearer $token", query)
                        .enqueue(object : Callback<SearchBookListResponse> {
                            override fun onResponse(call: Call<SearchBookListResponse>, response: Response<SearchBookListResponse>) {
                                Log.d("SearchFragment", "Book list search response: ${response.code()}")
                                if (response.isSuccessful) {
                                    Log.d("SearchFragment", "Book list search successful")
                                    val data = response.body()?.data ?: emptyList()
                                    fragment.updateBookList(data)
                                } else {
                                    Log.e("SearchFragment", "Book list search API Error: ${response.errorBody()?.string()}")
                                }
                            }

                            override fun onFailure(call: Call<SearchBookListResponse>, t: Throwable) {
                                Log.e("SearchFragment", "Book list search API Failure: ${t.message}")
                            }
                        })
                }
                is SearchMemoFragment -> {
                    Log.d("SearchFragment", "Searching book memos...")
                    RetrofitInstance.searchListApi.getBookMemos("Bearer $token", query)
                        .enqueue(object : Callback<SearchMemoResponse> {
                            override fun onResponse(call: Call<SearchMemoResponse>, response: Response<SearchMemoResponse>) {
                                Log.d("SearchFragment", "Memo search response: ${response.code()}")
                                if (response.isSuccessful) {
                                    Log.d("SearchFragment", "Memo search successful")
                                    val memos = response.body()?.data ?: emptyList()
                                    fragment.updateMemo(memos)
                                } else {
                                    Log.e("SearchFragment", "Memo search API Error: ${response.errorBody()?.string()}")
                                }
                            }

                            override fun onFailure(call: Call<SearchMemoResponse>, t: Throwable) {
                                Log.e("SearchFragment", "Memo search API Failure: ${t.message}")
                            }
                        })
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}