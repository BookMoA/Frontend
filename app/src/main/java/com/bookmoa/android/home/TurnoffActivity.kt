package com.bookmoa.android.home

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.bookmoa.android.databinding.ActivityTurnoffBinding
import kotlin.math.roundToInt

class TurnoffActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityTurnoffBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgTurnoff.setOnClickListener {
            val countDown: CountDownTimer = object : CountDownTimer(3250, 300) {
                override fun onTick(millisUntilFinished: Long) {
                    //binding.tv21.text = "${(millisUntilFinished.toFloat() / 1000.0f).roundToInt()}초 후에 해제됩니다."
                }

                override fun onFinish() {
                    startActivity(Intent(this@TurnoffActivity, HomeMemoActivity::class.java))
                    finish()
                }
            }

            countDown.start()
        }
    }
}