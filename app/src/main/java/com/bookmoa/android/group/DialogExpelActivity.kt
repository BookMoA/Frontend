package com.bookmoa.android.group

import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bookmoa.android.databinding.FragmentDialogexpelBinding
import com.bookmoa.android.databinding.FragmentToastBinding

class DialogExpelActivity : AppCompatActivity() {
    private val binding by lazy { FragmentDialogexpelBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
        val layoutParams = window.attributes
        layoutParams.dimAmount = 0.7f
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        setContentView(binding.root)

        val memberName = intent.getStringExtra("memberName") ?: "닉네임"
        binding.dialogexpelTv.text = "타 유저 ${memberName}님을(를)\n정말 내보내시겠어요?"

        binding.dialogexpelConfirmBtn.setOnClickListener {
            showCustomToast("타 유저 ${memberName}님을(를) 내보냈습니다.")
            setResult(Activity.RESULT_OK)
            finish()
        }

        binding.dialogexpelCancelBtn.setOnClickListener {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }

    private fun showCustomToast(message: String) {
        val toastBinding = FragmentToastBinding.inflate(LayoutInflater.from(this))
        toastBinding.toastTv.text = message

        Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            view = toastBinding.root
            show()
        }
    }
}