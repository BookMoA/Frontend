package com.bookmoa.android.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bookmoa.android.databinding.ActivityFocusmodeBinding

class FocusmodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityFocusmodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView12.setOnClickListener{
            val intent = Intent(this, TurnoffActivity::class.java)
            startActivity(intent)
        }
    }

}