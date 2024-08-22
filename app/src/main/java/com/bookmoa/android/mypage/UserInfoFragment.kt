package com.bookmoa.android.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bookmoa.android.R
import com.bookmoa.android.databinding.FragmentUserInfoBinding

class UserInfoFragment : Fragment() {

    lateinit var binding: FragmentUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserInfoBinding.inflate(layoutInflater, container, false)

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
}