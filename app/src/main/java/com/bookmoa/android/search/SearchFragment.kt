package com.bookmoa.android.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.bookmoa.android.R
import com.bookmoa.android.services.TokenManager
import com.bookmoa.android.adapter.SearchVpAdapter
import com.bookmoa.android.databinding.FragmentSearchBinding
import com.bookmoa.android.models.SearchBookData
import com.bookmoa.android.models.SearchBookListData
import com.bookmoa.android.models.SearchBookListResponse
import com.bookmoa.android.models.SearchBookResponse
import com.bookmoa.android.models.SearchMemoData
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
    private val searchViewModel: SearchViewModel by activityViewModels()

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

    private var titleResults: List<SearchBookData> = emptyList()
    private var authorResults: List<SearchBookData> = emptyList()
    private var bookListResults: List<SearchBookListData> = emptyList()
    private var memoResults: List<SearchMemoData> = emptyList()

    private fun searchBooks(query: String) {
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
                    fragment.searchBooksByName(query, "Title") { titleResults ->
                        searchViewModel.titleResults.value = titleResults
                    }
                }
                is SearchAuthorFragment -> {
                    fragment.searchBooksByName(query, "Author") { authorResults ->
                        searchViewModel.authorResults.value = authorResults
                    }
                }
                is SearchBookListFragment -> {
                    fragment.searchBookLists(query) { bookListResults ->
                        searchViewModel.bookListResults.value = bookListResults
                    }
                }
                is SearchMemoFragment -> {
                    fragment.searchMemoLists(query) { memoResults ->
                        searchViewModel.memoResults.value = memoResults
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}