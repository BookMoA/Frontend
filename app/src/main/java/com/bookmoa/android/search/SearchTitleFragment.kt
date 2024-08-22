package com.bookmoa.android.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.MainActivity
import com.bookmoa.android.adapter.SearchBookAdapter
import com.bookmoa.android.databinding.FragmentSearchTitleBinding
import com.bookmoa.android.models.SearchBook


class SearchTitleFragment : Fragment() {

    private var _binding: FragmentSearchTitleBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchBookAdapter: SearchBookAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchTitleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        searchBookAdapter = SearchBookAdapter(itemClickedListener = { book ->
            (activity as? MainActivity)?.showBookDetail(book.isbn13)
        })
        binding.searchTitleRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchBookAdapter
        }
    }

    fun updateBookList(books: List<SearchBook>) {
        // 데이터 로그 추가
        Log.d("SearchTitleFragment", "Updating book list. Books count: ${books.size}")

        if (books.isNullOrEmpty()) {
            Log.d("SearchTitleFragment", "No books available.")
            binding.searchTitleAvailable.visibility = View.GONE
            binding.searchTitleNotAvailable.visibility = View.VISIBLE
        } else {
            Log.d("SearchTitleFragment", "Books available: ${books.size}")
            binding.searchTitleAvailable.visibility = View.VISIBLE
            binding.searchTitleNotAvailable.visibility = View.GONE
            searchBookAdapter.submitList(books)
        }
    }


}

