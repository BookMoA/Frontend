package com.bookmoa.android.home

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerFragment(private val onDateSetListener: (year: Int, month: Int, day: Int, viewId: Int) -> Unit) : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 현재 날짜를 가져오기
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        // DatePickerDialog 생성
        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        // 선택된 날짜를 호출된 함수에 전달
        val viewId = requireArguments().getInt("viewId")
        onDateSetListener(year, month, dayOfMonth, viewId)
    }

    companion object {
        fun newInstance(onDateSetListener: (year: Int, month: Int, day: Int, viewId: Int) -> Unit, viewId: Int): DatePickerFragment {
            val fragment = DatePickerFragment(onDateSetListener)
            val args = Bundle().apply {
                putInt("viewId", viewId)
            }
            fragment.arguments = args
            return fragment
        }
    }
}