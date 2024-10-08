package com.bookmoa.android.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bookmoa.android.databinding.FragmentMemoReadBinding

class MemoReadFragment : Fragment() {

    lateinit var binding: FragmentMemoReadBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMemoReadBinding.inflate(inflater,container,false)

        return binding.root
    }
}