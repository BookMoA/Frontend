package com.bookmoa.android.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bookmoa.android.R

class MypageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MypageFragment())
                .commit()
        }
    }
}