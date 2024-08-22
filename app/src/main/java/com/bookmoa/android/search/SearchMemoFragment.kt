package com.bookmoa.android.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.MainActivity
import com.bookmoa.android.adapter.SearchMemoAdapter
import com.bookmoa.android.databinding.FragmentSearchMemoBinding
import com.bookmoa.android.models.SearchMemoData


class SearchMemoFragment: Fragment() {

    private var _binding: FragmentSearchMemoBinding? = null
    private val binding get() = _binding!!
    private lateinit var bookAdapter: SearchMemoAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchMemoBinding.inflate(inflater,container,false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }


    private fun setupRecyclerView() {
        bookAdapter = SearchMemoAdapter(itemClickedListener = { book ->
            (activity as? MainActivity)?.showBookDetail(book.title)
        })
        binding.searchTitleRv.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bookAdapter
        }
    }

    fun updateMemo(data: List<SearchMemoData>) {
        if (data.isNullOrEmpty()) {
            binding.searchMemoAvailable.visibility = View.GONE
            binding.searchMemoNotAvailable.visibility = View.VISIBLE
        } else {
            binding.searchMemoAvailable.visibility = View.VISIBLE
            binding.searchMemoNotAvailable.visibility = View.GONE
            bookAdapter.updateItems(data)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}