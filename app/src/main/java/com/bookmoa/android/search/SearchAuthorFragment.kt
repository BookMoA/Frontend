package com.bookmoa.android.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bookmoa.android.databinding.FragmentSearchAuthorBinding


class SearchAuthorFragment: Fragment() {

    lateinit var binding: FragmentSearchAuthorBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchAuthorBinding.inflate(inflater,container,false)

        return binding.root
    }
}