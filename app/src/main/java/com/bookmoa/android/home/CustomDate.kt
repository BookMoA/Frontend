package com.bookmoa.android.home

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.bookmoa.android.R

class CustomDate (context: Context, private val listener: (Int, Int, Int) -> Unit) : Dialog(context) {
    private lateinit var spinnerDay: Spinner
    private lateinit var spinnerMonth: Spinner
    private lateinit var spinnerYear: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_date_picker)

        spinnerDay = findViewById(R.id.spinnerDay)
        spinnerMonth = findViewById(R.id.spinnerMonth)
        spinnerYear = findViewById(R.id.spinnerYear)

        val days = (1..31).map { "$it 일" }.toList()
        val months = (1..12).map { "$it 월" }.toList()
        val years = (2020..2025).map { "$it 년" }.toList()

        spinnerDay.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, days)
        spinnerMonth.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, months)
        spinnerYear.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, years)

        val buttonOK: Button = findViewById(R.id.btnOk)
        buttonOK.setOnClickListener {
            val day = spinnerDay.selectedItem.toString().replace(" 일", "").toInt()
            val month = spinnerMonth.selectedItem.toString().replace(" 월", "").toInt()
            val year = spinnerYear.selectedItem.toString().replace(" 년", "").toInt()
            listener(year, month, day)
            dismiss()
        }

        val buttonCancel: Button = findViewById(R.id.btnCancel)
        buttonCancel.setOnClickListener {
            dismiss()
        }
    }
}