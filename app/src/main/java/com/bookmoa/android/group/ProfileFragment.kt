package com.bookmoa.android.group

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.bookmoa.android.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private var memberName: String? = null
    private var floatMsg: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            memberName = it.getString("memberName")
            floatMsg = it.getString("floatMsg")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            profileNameTv.text = memberName ?: ""
            profileFloatEt.setText(floatMsg ?: "")
            profileBackIv.setOnClickListener { parentFragmentManager.popBackStack() }
            profileDoneTv.setOnClickListener { parentFragmentManager.popBackStack() }
            profileFloatEt.requestFocus()
            val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(profileFloatEt, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(memberName: String, floatMsg: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString("memberName", memberName)
                    putString("floatMsg", floatMsg)
                }
            }
    }
}