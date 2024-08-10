package com.bookmoa.android.group

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.bookmoa.android.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private val binding by lazy { ActivityProfileBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val memberName = intent.getStringExtra("memberName") ?: ""
        val floatMsg = intent.getStringExtra("floatMsg") ?: ""

        binding.apply {
            profileNameTv.text = memberName
            profileFloatEt.setText(floatMsg)
            profileBackIv.setOnClickListener { finish() }
            profileDoneTv.setOnClickListener {
                finish()
            }
            binding.profileFloatEt.requestFocus() // 포커스 요청
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(binding.profileFloatEt, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}