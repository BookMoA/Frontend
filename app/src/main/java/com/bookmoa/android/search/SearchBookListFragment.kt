package com.bookmoa.android.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.MainActivity
import com.bookmoa.android.SearchBookListAdapter
import com.bookmoa.android.databinding.FragmentSearchBookListBinding
import com.bookmoa.android.models.SearchBookListData


class SearchBookListFragment : Fragment() {

    private var _binding: FragmentSearchBookListBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookListAdapter: SearchBookListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBookListBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        bookListAdapter = SearchBookListAdapter(itemClickedListener = { book ->
            (activity as? MainActivity)?.showBookDetail(book.title)
        })
        binding.searchBookListRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bookListAdapter
        }
    }

    fun updateBookList(data: List<SearchBookListData>) {
        if (data.isNullOrEmpty()) {
            binding.searchBookListAvailable.visibility = View.GONE
            binding.searchBookListNotAvailable.visibility = View.VISIBLE
        } else {
            binding.searchBookListAvailable.visibility = View.VISIBLE
            binding.searchBookListNotAvailable.visibility = View.GONE
            bookListAdapter.submitList(data)
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}