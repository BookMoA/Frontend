package com.bookmoa.android.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.MainActivity
import com.bookmoa.android.adapter.SearchBookAdapter
import com.bookmoa.android.adapter.SearchBookListAdapter
import com.bookmoa.android.adapter.SearchMemoAdapter
import com.bookmoa.android.databinding.FragmentSearchTotalBinding

class SearchTotalFragment : Fragment() {

    private var _binding: FragmentSearchTotalBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by activityViewModels() // ViewModel 초기화

    private lateinit var titleAdapter: SearchBookAdapter
    private lateinit var authorAdapter: SearchBookAdapter
    private lateinit var bookListAdapter: SearchBookListAdapter
    private lateinit var memoAdapter: SearchMemoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchTotalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        observeViewModel()
    }

    private fun setupAdapters() {
        titleAdapter = SearchBookAdapter(itemClickedListener = { book ->
            (activity as? MainActivity)?.showBookDetail(book.isbn13)
        })
        authorAdapter = SearchBookAdapter(itemClickedListener = { book ->
            (activity as? MainActivity)?.showBookDetail(book.isbn13)
        })
        bookListAdapter = SearchBookListAdapter(itemClickedListener = {

        })
        memoAdapter = SearchMemoAdapter(itemClickedListener = {

        })

        binding.searchTotalTitleRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = titleAdapter
        }
        binding.searchTotalAuthorRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = authorAdapter
        }
        binding.searchTotalBookListRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bookListAdapter
        }
        binding.searchTotalMemoRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = memoAdapter
        }
    }

    private fun observeViewModel() {
        searchViewModel.titleResults.observe(viewLifecycleOwner) { titles ->
            updateLayoutVisibility(binding.searchTotalTitleRvContainer, titles)
            titleAdapter.submitList(titles.take(3))
        }
        searchViewModel.authorResults.observe(viewLifecycleOwner) { authors ->
            updateLayoutVisibility(binding.searchTotalAuthorRvContainer, authors)
            authorAdapter.submitList(authors.take(3))
        }
        searchViewModel.bookListResults.observe(viewLifecycleOwner) { bookLists ->
            updateLayoutVisibility(binding.searchTotalBookListRvContainer, bookLists)
            bookListAdapter.submitList(bookLists.take(3))
        }
        searchViewModel.memoResults.observe(viewLifecycleOwner) { memos ->
            updateLayoutVisibility(binding.searchTotalMemoRvContainer, memos)
            memoAdapter.updateItems(memos.take(3))
        }
    }

    private fun updateLayoutVisibility(layout: View, dataList: List<Any>) {
        if (dataList.isNullOrEmpty()) {
            layout.visibility = View.GONE
        } else {
            layout.visibility = View.VISIBLE
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
