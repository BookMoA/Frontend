package com.bookmoa.android.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bookmoa.android.R

class MemoInfoFragment : Fragment() {

    private lateinit var tvStartDate: TextView
    private lateinit var tvEndDate: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_memo_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TextView를 초기화
        tvStartDate = view.findViewById(R.id.tvStartDate)
        tvEndDate = view.findViewById(R.id.tvEndDate)
    }

    fun setStartDate(date: String) {
        // TextView가 초기화된 후에만 데이터 설정
        if (::tvStartDate.isInitialized) {
            tvStartDate.text = date
        } else {
            Log.d("MemoInfoFragment", "tvStartDate not initialized in setStartDate")
        }
    }

    fun setEndDate(date: String) {
        // TextView가 초기화된 후에만 데이터 설정
        if (::tvEndDate.isInitialized) {
            tvEndDate.text = date
        } else {
            Log.d("MemoInfoFragment", "tvEndDate not initialized in setEndDate")
        }
    }
}
