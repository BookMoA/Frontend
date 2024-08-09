package com.bookmoa.android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bookmoa.android.auth.OnboardingActivity
import com.bookmoa.android.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.testTv.setOnClickListener {
            val intent = Intent(this@MainActivity, OnboardingActivity::class.java)
            startActivity(intent)
        }
    }
}