package com.bookmoa.android.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bookmoa.android.databinding.ActivityPassbookBinding

class PassbookActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPassbookBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView8.text="BB"
        binding.textView8.text="dafd"




    }
}