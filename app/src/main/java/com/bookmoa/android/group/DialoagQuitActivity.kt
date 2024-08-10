package com.bookmoa.android.group

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bookmoa.android.databinding.FragmentDialogquitBinding
import com.bookmoa.android.databinding.FragmentToastBinding
import com.bookmoa.bookmoa.GroupActivity

class DialogQuitActivity: AppCompatActivity() {
    private lateinit var binding: FragmentDialogquitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogquitBinding.inflate(layoutInflater)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
        val layoutParams = window.attributes
        layoutParams.dimAmount = 0.7f
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        setContentView(binding.root)

        binding.dialogquitCancelBtn.setOnClickListener {
            finish()
        }

        binding.dialogquitConfirmBtn.setOnClickListener {
            showCustomToast("그룹 탈퇴가 완료됐습니다.")
            val intent = Intent(this, GroupActivity::class.java)
            startActivity(intent)
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