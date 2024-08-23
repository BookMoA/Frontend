package com.bookmoa.android.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bookmoa.android.R
import com.bookmoa.android.databinding.ActivityHomeMemoBinding

class HomeMemoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeMemoBinding
    private lateinit var dbHelper: BookDatabaseHelper
    private var selectedButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DB 초기화
        dbHelper = BookDatabaseHelper(this)

        // 시작 날짜 클릭 시 DatePickerDialog를 보여주는 코드
        binding.etStart.setOnClickListener {
            showCustomDatePicker(it)
        }

        // 완료 날짜 클릭 시 DatePickerDialog를 보여주는 코드
        binding.etEnd.setOnClickListener {
            showCustomDatePicker(it)
        }

        // 버튼 선택 했을 때 색깔 변하는거
        binding.btnDone.setOnClickListener { onButtonClicked(binding.btnDone, "다 읽은 책") }
        binding.btnProgress.setOnClickListener { onButtonClicked(binding.btnProgress, "읽고 있는 책") }
        binding.btnWish.setOnClickListener { onButtonClicked(binding.btnWish, "읽고 싶은 책") }

        binding.imgBookCover.setOnClickListener {

            val pageInput = binding.etPage.text.toString().toIntOrNull() ?: 0
            dbHelper.insertPage(pageInput)

            val selectedRating = binding.rtbBefore.rating // MainActivity에서 별 개수 가져오기

            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("EXTRA_RATING", selectedRating) // 별 개수를 Intent에 추가
            }
            startActivity(intent)
        }
    }

    private fun showCustomDatePicker(view: View) {
        val viewId = view.id
        val datePickerDialog = CustomDate(this) { year, month, day ->
            val dateMessage = "$year/${month.toString().padStart(2, '0')}/${day.toString().padStart(2, '0')}"
            if (viewId == R.id.etStart) {
                binding.etStart.setText(dateMessage)
                dbHelper.insertStartDate(dateMessage)
            } else if (viewId == R.id.etEnd) {
                binding.etEnd.setText(dateMessage)
                dbHelper.insertEndDate(dateMessage)
            }
        }
        datePickerDialog.show()
    }

    // 버튼 색깔 유지하고 데이터베이스에 저장
    private fun onButtonClicked(button: Button, status: String) {
        selectedButton?.isSelected = false
        selectedButton?.setBackgroundResource(R.drawable.layout_btn_default)

        button.isSelected = true
        button.setBackgroundResource(R.drawable.layout_btn_pressed)

        selectedButton = button

        // 선택한 버튼 상태를 DB에 저장
        dbHelper.insertStatus(status)
    }
}