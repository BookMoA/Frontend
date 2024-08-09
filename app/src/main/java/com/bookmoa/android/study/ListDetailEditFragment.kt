package com.bookmoa.android.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.bookmoa.android.databinding.FragmentListDetailEditBinding

class ListDetailEditFragment : Fragment() {
    lateinit var binding: FragmentListDetailEditBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListDetailEditBinding.inflate(inflater, container, false)

        return binding.root
    }
}