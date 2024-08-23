package com.bookmoa.android.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bookmoa.android.R
import com.bookmoa.android.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var dbHelper: BookDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = BookDatabaseHelper(this)

        val lastStatus = dbHelper.getLastStatus()
        binding.tvStatus.text = lastStatus ?: "정보 없음"

        val lastPage = dbHelper.getLastPage()
        binding.tvCurrentPage.text = lastPage.toString()

        val lastStartDate = dbHelper.getLastStartDate()
        val lastEndDate = dbHelper.getLastEndDate()

        // Fragment가 이미 추가된 경우 데이터 설정
        var fragment = supportFragmentManager.findFragmentById(R.id.fragInfo) as? MemoInfoFragment

        if (fragment == null) {
            // Fragment가 없는 경우, 새로 생성하고 데이터 설정
            fragment = MemoInfoFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragInfo, fragment)
                .commitAllowingStateLoss()

            // Fragment가 추가된 후에 데이터 설정
            supportFragmentManager.executePendingTransactions() // Fragment 트랜잭션 완료 대기
        }

        fragment?.let {
            it.setStartDate(lastStartDate ?: "정보 없음")
            it.setEndDate(lastEndDate ?: "정보 없음")
        }

        // Intent로 전달된 별 개수 받아오기
        val rating = intent.getFloatExtra("EXTRA_RATING", 0f)
        binding.rtbAfter.rating = rating // RatingBar에 별 개수 설정
    }
}