package com.bookmoa.android.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bookmoa.android.MainActivity
import com.bookmoa.android.databinding.FragmentStudyVp1Binding

class StudyVp1Fragment : Fragment() {

    lateinit var binding: FragmentStudyVp1Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyVp1Binding.inflate(inflater, container, false)

        binding.imgBanner1.setOnClickListener {
            (activity as MainActivity).switchFragment(RecommendFragment())
        }

        return binding.root
    }
}