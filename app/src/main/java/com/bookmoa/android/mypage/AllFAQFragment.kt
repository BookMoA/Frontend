package com.bookmoa.android.mypage

import android.os.Bundle
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bookmoa.android.R
import com.bookmoa.android.databinding.FragmentAllFAQBinding

class AllFAQFragment : Fragment() {
    lateinit var binding: FragmentAllFAQBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllFAQBinding.inflate(inflater, container, false)


        binding.questionLayout1.setOnClickListener {
            if (binding.answerTv1.visibility == View.GONE) {
                binding.answerTv1.visibility = View.VISIBLE
                binding.question1Guideline.visibility = View.VISIBLE
                binding.arrowIcon1.setImageResource(R.drawable.ic_up_arrow)
            } else {
                binding.answerTv1.visibility = View.GONE
                binding.question1Guideline.visibility = View.GONE
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

        binding.questionLayout4.setOnClickListener {
            if (binding.answerTv4.visibility == View.GONE) {
                binding.answerTv4.visibility = View.VISIBLE
                binding.userQuitGuideIc.visibility = View.VISIBLE
                binding.userQuitGuideTv.visibility = View.VISIBLE
                binding.question4Guideline.visibility = View.VISIBLE
                binding.answerTv41.visibility = View.VISIBLE
                binding.question4Guideline1.visibility = View.VISIBLE
                binding.userQuitErrorGuideIc.visibility = View.VISIBLE
                binding.userQuitErrorGuideTv.visibility = View.VISIBLE
                binding.quitCommentTv.visibility = View.VISIBLE
                binding.arrowIcon4.setImageResource(R.drawable.ic_up_arrow)
            } else {
                binding.answerTv4.visibility = View.GONE
                binding.userQuitGuideIc.visibility = View.GONE
                binding.userQuitGuideTv.visibility = View.GONE
                binding.question4Guideline.visibility = View.GONE
                binding.answerTv41.visibility = View.GONE
                binding.question4Guideline1.visibility = View.GONE
                binding.userQuitErrorGuideIc.visibility = View.GONE
                binding.userQuitErrorGuideTv.visibility = View.GONE
                binding.quitCommentTv.visibility = View.GONE
                binding.arrowIcon4.setImageResource(R.drawable.ic_down_arrow)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*
        setupQuestion(binding.questionLayout1, binding.answerTv1, binding.arrowIcon1)
        setupQuestion(binding.questionLayout2, binding.answerTv2, binding.arrowIcon2)
        setupQuestion(binding.questionLayout3, binding.answerTv3, binding.arrowIcon3)
        setupQuestion(binding.questionLayout4, binding.answerTv4, binding.arrowIcon4)
         */
    }

    /*
    private fun setupQuestion(questionLayout: View, answerText: View, arrowIcon: ImageView) {
        var isExpanded = false

        questionLayout.setOnClickListener {

            TransitionManager.beginDelayedTransition(binding.root)

            if (isExpanded) {
                answerText.visibility = View.GONE
                arrowIcon.setImageResource(R.drawable.ic_down_arrow)
                questionLayout.setBackgroundResource(R.drawable.bg_button_color_r20)
            } else {
                answerText.visibility = View.VISIBLE
                arrowIcon.setImageResource(R.drawable.ic_up_arrow)
                questionLayout.setBackgroundResource(R.drawable.border_expanded_black_r20)
            }

            isExpanded = !isExpanded
        }
    }
     */
}