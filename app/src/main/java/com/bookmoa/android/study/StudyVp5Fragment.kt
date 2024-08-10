package com.bookmoa.android.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bookmoa.android.MainActivity
import com.bookmoa.android.databinding.FragmentStudyVp5Binding


class StudyVp5Fragment : Fragment() {

    lateinit var binding: FragmentStudyVp5Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyVp5Binding.inflate(inflater, container, false)

        binding.imgBanner5.setOnClickListener {
            (activity as MainActivity).switchFragment(RecommendFragment())
        }

        return binding.root
    }
}