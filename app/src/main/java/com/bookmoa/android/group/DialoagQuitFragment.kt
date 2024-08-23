package com.bookmoa.android.group

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bookmoa.android.MainActivity
import com.bookmoa.android.databinding.FragmentDialogquitBinding
import com.bookmoa.android.databinding.FragmentToastBinding

class DialogQuitFragment : DialogFragment() {
    private var _binding: FragmentDialogquitBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogquitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
            setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            attributes.dimAmount = 0.7f
            addFlags(android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }

        binding.dialogquitCancelBtn.setOnClickListener {
            dismiss()
        }

        binding.dialogquitConfirmBtn.setOnClickListener {
            showCustomToast("그룹 탈퇴가 완료됐습니다.")
            (activity as? MainActivity)?.switchFragment(NextLeaderFragment())
            dismiss()
        }
    }

    private fun showCustomToast(message: String) {
        val toastBinding = FragmentToastBinding.inflate(LayoutInflater.from(requireContext()))
        toastBinding.toastTv.text = message

        Toast(requireContext()).apply {
            duration = Toast.LENGTH_SHORT
            view = toastBinding.root
            show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}