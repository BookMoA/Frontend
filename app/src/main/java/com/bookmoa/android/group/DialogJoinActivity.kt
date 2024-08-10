package com.bookmoa.android.group

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bookmoa.android.databinding.FragmentDialogjoinBinding

class DialogJoinActivity: AppCompatActivity() {
    private lateinit var binding: FragmentDialogjoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentDialogjoinBinding.inflate(layoutInflater)
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        window.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
        val layoutParams = window.attributes
        layoutParams.dimAmount = 0.7f
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        setContentView(binding.root)

        val title = intent.getStringExtra("TITLE") ?: ""
        val count = intent.getStringExtra("COUNT") ?: ""
        val description = intent.getStringExtra("DESCRIPTION") ?: ""

        binding.dialogjoinTitleTv.text = title
        binding.dialogjoinCountTv.text = count
        binding.dialogjoinDescriptionTv.text = description

        binding.dialogjoinJoinBtn.setOnClickListener {
            val intent = Intent(this, CommunityActivity::class.java)
            startActivity(intent)
        }

        binding.dialogjoinCancelBtn.setOnClickListener {
            finish()
        }
    }
}