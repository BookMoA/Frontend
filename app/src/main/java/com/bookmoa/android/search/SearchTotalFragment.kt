package com.bookmoa.android.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bookmoa.android.databinding.FragmentSearchTotalBinding

class SearchTotalFragment: Fragment() {
    lateinit var binding: FragmentSearchTotalBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchTotalBinding.inflate(inflater,container,false)

        return binding.root
    }
}