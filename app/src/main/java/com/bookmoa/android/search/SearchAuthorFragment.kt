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
import com.bookmoa.android.databinding.FragmentSearchAuthorBinding
import com.bookmoa.android.models.SearchBook


class SearchAuthorFragment : Fragment() {

    private var _binding: FragmentSearchAuthorBinding? = null
    private val binding get() = _binding!!

    private lateinit var searchBookAdapter: SearchBookAdapter

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
    }

    private fun setupRecyclerView() {
        searchBookAdapter =SearchBookAdapter(itemClickedListener = { book ->
            (activity as? MainActivity)?.showBookDetail(book.isbn13)
        })
        binding.searchAuthorRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = searchBookAdapter
        }
    }

    fun updateBookList(books: List<SearchBook>) {
        Log.d("SearchAuthorFragment", "Updating book list with ${books.size} items")
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