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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupQuestion(binding.questionLayout1, binding.answerTv1, binding.arrowIcon1)
        setupQuestion(binding.questionLayout2, binding.answerTv2, binding.arrowIcon2)
        setupQuestion(binding.questionLayout3, binding.answerTv3, binding.arrowIcon3)
        setupQuestion(binding.questionLayout4, binding.answerTv4, binding.arrowIcon4)
    }

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
}