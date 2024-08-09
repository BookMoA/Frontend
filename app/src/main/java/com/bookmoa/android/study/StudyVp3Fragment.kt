package com.bookmoa.android.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bookmoa.android.MainActivity
import com.bookmoa.android.databinding.FragmentStudyVp3Binding


class StudyVp3Fragment : Fragment() {

    lateinit var binding: FragmentStudyVp3Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyVp3Binding.inflate(inflater, container, false)

        binding.imgBanner3.setOnClickListener {
            (activity as MainActivity).switchFragment(RecommendFragment())
        }
        return binding.root
    }
}