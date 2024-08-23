package com.bookmoa.android.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bookmoa.android.services.TokenManager
import com.bookmoa.android.adapter.SearchVpAdapter
import com.bookmoa.android.databinding.FragmentSearchBinding
import com.bookmoa.android.services.AladinBookService
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


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
                0 -> "제목"
                1 -> "지은이"
                2 -> "북 리스트"
                3 -> "독서 메모"
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
                    showLoading(true) // Show progress bar
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
        Log.d("SearchFragment", "Searching with query: $query")

        val fragments = listOf(
            childFragmentManager.findFragmentByTag("f0") as? SearchTitleFragment,
            childFragmentManager.findFragmentByTag("f1") as? SearchAuthorFragment,
            childFragmentManager.findFragmentByTag("f2") as? SearchBookListFragment,
            childFragmentManager.findFragmentByTag("f3") as? SearchMemoFragment
        )

        fragments.forEach { fragment ->
            when (fragment) {
                is SearchTitleFragment -> {
                    fragment.searchBooksByName(query, "Title") { results ->
                        showLoading(false) // Hide progress bar
                    }
                }
                is SearchAuthorFragment -> {
                    fragment.searchBooksByName(query, "Author") { results ->
                        showLoading(false) // Hide progress bar
                    }
                }
                is SearchBookListFragment -> {
                    fragment.searchBookLists(query) { results ->
                        showLoading(false) // Hide progress bar
                    }
                }
                is SearchMemoFragment -> {
                    fragment.searchMemoLists(query) { results ->
                        showLoading(false) // Hide progress bar
                    }
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}