package com.bookmoa.android.group

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bookmoa.android.databinding.FragmentDialogexpelBinding
import com.bookmoa.android.databinding.FragmentToastBinding

class DialogExpelFragment : DialogFragment() {
    private var _binding: FragmentDialogexpelBinding? = null
    private val binding get() = _binding!!
    private var onConfirmClick: (() -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDialogexpelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val memberName = arguments?.getString("memberName") ?: "닉네임"
        binding.dialogexpelTv.text = "타 유저 ${memberName}님을(를)\n정말 내보내시겠어요?"

        binding.dialogexpelConfirmBtn.setOnClickListener {
            onConfirmClick?.invoke()
            showCustomToast("타 유저 ${memberName}님을(를) 내보냈습니다.")
            dismiss()
        }

        binding.dialogexpelCancelBtn.setOnClickListener {
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes.apply {
                width = (resources.displayMetrics.widthPixels * 0.90).toInt()
                height = WindowManager.LayoutParams.WRAP_CONTENT
                dimAmount = 0.7f
                flags = flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
            }
        }
        return dialog
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

    fun setConfirmClickListener(listener: () -> Unit) {
        onConfirmClick = listener
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}