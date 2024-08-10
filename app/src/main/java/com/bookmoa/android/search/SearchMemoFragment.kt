package com.bookmoa.android.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bookmoa.android.databinding.FragmentSearchMemoBinding

class SearchMemoFragment: Fragment() {

    lateinit var binding: FragmentSearchMemoBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchMemoBinding.inflate(inflater,container,false)

        return binding.root
    }
}