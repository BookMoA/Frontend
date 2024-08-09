package com.bookmoa.and.group

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bookmoa.and.databinding.ActivityWriteBinding

class WriteActivity: AppCompatActivity() {
    private val binding: ActivityWriteBinding by lazy {
        ActivityWriteBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.writeCloseIv.setOnClickListener {
            finish()
        }
        binding.writeDoneTv.setOnClickListener {
            finish()
        }
    }
}
