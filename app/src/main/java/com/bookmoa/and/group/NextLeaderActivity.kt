package com.bookmoa.and.group

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bookmoa.and.databinding.ActivityNextleaderBinding

class NextLeaderActivity: AppCompatActivity() {
    private val binding: ActivityNextleaderBinding by lazy{
        ActivityNextleaderBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.nextleaderBackIv.setOnClickListener{
            val intent = Intent(this, CommunityActivity::class.java)
            startActivity(intent)
        }
    }
}