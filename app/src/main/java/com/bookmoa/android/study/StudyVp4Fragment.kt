package com.bookmoa.android.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bookmoa.android.MainActivity
import com.bookmoa.android.databinding.FragmentStudyVp4Binding


class StudyVp4Fragment : Fragment() {

    lateinit var binding: FragmentStudyVp4Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyVp4Binding.inflate(inflater, container, false)

        binding.imgBanner4.setOnClickListener {
            (activity as MainActivity).switchFragment(RecommendFragment())
        }

        return binding.root
    }
}