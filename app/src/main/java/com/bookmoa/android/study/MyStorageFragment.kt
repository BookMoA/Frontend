package com.bookmoa.android.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bookmoa.android.MainActivity
import com.bookmoa.android.adapter.MyStorageVpAdapter
import com.bookmoa.android.databinding.FragmentMyStorageBinding
import com.google.android.material.tabs.TabLayoutMediator

class MyStorageFragment : Fragment() {

    lateinit var binding: FragmentMyStorageBinding
    private val information = arrayListOf("전체", "읽은 책", "읽고 있는 책", "내 리스트")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyStorageBinding.inflate(inflater, container, false)

        binding.myStorageBackIcon.setOnClickListener {
            (activity as MainActivity).switchFragment(StudyFragment())
        }
        val lockerAdapter = MyStorageVpAdapter(this)
        binding.myStorageContentVp.adapter = lockerAdapter

        TabLayoutMediator(binding.myStorageContentTb, binding.myStorageContentVp)
        { tab, position ->
            tab.text = information[position]
        }.attach()

        return binding.root
    }
}