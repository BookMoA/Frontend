package com.bookmoa.android.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.adapter.AlarmFragmentAdapter
import com.bookmoa.android.adapter.AlarmItems
import com.bookmoa.android.databinding.FragmentAlarmBinding

class AlarmFragment: Fragment() {
    private var _binding: FragmentAlarmBinding? = null
    private val binding get() = _binding!!

    private lateinit var alarmAdapter: AlarmFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlarmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.alarmRv.layoutManager = LinearLayoutManager(context)

        val alarmItems = listOf(
            AlarmItems(true,"HI","2023","Hello",true),
            AlarmItems(true,"HI","2023","Hello",true),
            AlarmItems(true,"HI","2023","Hello",true),
            AlarmItems(true,"HI","2023","Hello",true),
            AlarmItems(true,"HI","2023","Hello",true)
        )

        alarmAdapter = AlarmFragmentAdapter(alarmItems)
        binding.alarmRv.adapter = alarmAdapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}