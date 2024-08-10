package com.bookmoa.android.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.adapter.SearchNoticeFragmentAdapter
import com.bookmoa.android.adapter.SearchNoticeItems
import com.bookmoa.android.databinding.FragmentSearchnoticevpBinding

class SearchNoticeFragment: Fragment() {
    private var _binding: FragmentSearchnoticevpBinding? = null
    private val binding get() = _binding!!

    private lateinit var noticeAdapter: SearchNoticeFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchnoticevpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchnoticeRv.layoutManager = LinearLayoutManager(context)

        val noticeItems = listOf(
            SearchNoticeItems("hi", "hi", "hi"),
            SearchNoticeItems("hi", "hi", "hi"),
            SearchNoticeItems("hi", "hi", "hi")
        )

        noticeAdapter = SearchNoticeFragmentAdapter(noticeItems)
        binding.searchnoticeRv.adapter = noticeAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}