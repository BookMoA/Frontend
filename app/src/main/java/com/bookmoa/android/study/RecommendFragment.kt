package com.bookmoa.android.study

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bookmoa.android.MainActivity
import com.bookmoa.android.databinding.FragmentRecommendBinding

class RecommendFragment : Fragment() {
    lateinit var binding: FragmentRecommendBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecommendBinding.inflate(inflater, container, false)

        binding.recommendBackIcon.setOnClickListener {
            (activity as MainActivity).switchFragment(StudyFragment())
        }
        binding.recommendBookStoreBtn.setOnClickListener {
            (activity as MainActivity).switchFragment(MyListStorageFragment())
        }
        binding.recommendGoToAladdin.paintFlags = Paint.UNDERLINE_TEXT_FLAG

        return binding.root
    }
}