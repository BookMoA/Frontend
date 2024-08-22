package com.bookmoa.android.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bookmoa.android.R
import com.bookmoa.android.databinding.FragmentUseInfoFAQBinding

class UseInfoFAQFragment : Fragment() {

    lateinit var binding: FragmentUseInfoFAQBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUseInfoFAQBinding.inflate(layoutInflater, container, false)

        binding.questionLayout1.setOnClickListener {
            if (binding.answerTv1.visibility == View.GONE) {
                binding.answerTv1.visibility = View.VISIBLE
                binding.question1Guideline.visibility = View.VISIBLE
                binding.bookRegisterGuideIc.visibility = View.VISIBLE
                binding.bookRegisterGuideTv.visibility = View.VISIBLE
                binding.arrowIcon1.setImageResource(R.drawable.ic_up_arrow)
            } else {
                binding.answerTv1.visibility = View.GONE
                binding.question1Guideline.visibility = View.GONE
                binding.bookRegisterGuideIc.visibility = View.GONE
                binding.bookRegisterGuideTv.visibility = View.GONE
                binding.arrowIcon1.setImageResource(R.drawable.ic_down_arrow)
            }
        }

        binding.questionLayout2.setOnClickListener {
            if (binding.answerTv2.visibility == View.GONE) {
                binding.answerTv2.visibility = View.VISIBLE
                binding.question2Guideline.visibility = View.VISIBLE
                binding.arrowIcon2.setImageResource(R.drawable.ic_up_arrow)
            } else {
                binding.answerTv2.visibility = View.GONE
                binding.question2Guideline.visibility = View.GONE
                binding.arrowIcon2.setImageResource(R.drawable.ic_down_arrow)
            }
        }

        binding.questionLayout3.setOnClickListener {
            if (binding.answerTv3.visibility == View.GONE) {
                binding.answerTv3.visibility = View.VISIBLE
                binding.question3Guideline.visibility = View.VISIBLE
                binding.bookRegisterGuideIc.visibility = View.VISIBLE
                binding.bookRegisterGuideTv.visibility = View.VISIBLE
                binding.arrowIcon3.setImageResource(R.drawable.ic_up_arrow)
            } else {
                binding.answerTv3.visibility = View.GONE
                binding.question3Guideline.visibility = View.GONE
                binding.bookRegisterGuideIc.visibility = View.GONE
                binding.bookRegisterGuideTv.visibility = View.GONE
                binding.arrowIcon3.setImageResource(R.drawable.ic_down_arrow)
            }
        }

        return binding.root
    }

}