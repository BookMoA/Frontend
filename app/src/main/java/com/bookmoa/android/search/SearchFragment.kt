package com.bookmoa.android.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bookmoa.android.MainActivity
import com.bookmoa.android.adapter.SearchVpAdapter
import com.bookmoa.android.study.StudyFragment
import com.bookmoa.android.databinding.FragmentSearchBinding
import com.google.android.material.tabs.TabLayoutMediator

class SearchFragment: Fragment() {

    lateinit var binding: FragmentSearchBinding
    private val search = arrayListOf("전체", "제목", "지은이", "북리스트","메모")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)

        binding.searchBackIcon.setOnClickListener {
            (activity as MainActivity).switchFragment(StudyFragment())
        }

        val lockerAdapter = SearchVpAdapter(this)
        binding.searchContentVp.adapter = lockerAdapter

        TabLayoutMediator(binding.searchContentTb, binding.searchContentVp)
        {
            tab, position ->
            tab.text = search[position]
        }.attach()

        return binding.root
    }
}