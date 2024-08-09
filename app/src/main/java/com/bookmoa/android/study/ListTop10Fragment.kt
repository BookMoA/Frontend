package com.bookmoa.android.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.MainActivity
import com.bookmoa.android.R
import com.bookmoa.android.adapter.ListTop10Adapter
import com.bookmoa.android.databinding.FragmentListTop10Binding

class ListTop10Fragment : Fragment() {

    lateinit var binding: FragmentListTop10Binding
    private var listTop10Adapter: ListTop10Adapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListTop10Binding.inflate(inflater, container, false)
        binding.listTop10BackIcon.setOnClickListener {
            (activity as MainActivity).switchFragment(StudyFragment())

        }
        listTop10Adapter = ListTop10Adapter()

        // RecyclerView에 레이아웃 매니저 설정
        binding.listTop10Rv.layoutManager = LinearLayoutManager(context)

        // RecyclerView에 어댑터 설정
        binding.listTop10Rv.adapter = listTop10Adapter


        // 임의의 데이터 어댑터에 추가
        getItem()


        return binding.root
    }

    private fun getItem() {
        var data = ListTop10Dao(R.drawable.ic_launcher_foreground, "책 이름", 45, 3)
        listTop10Adapter?.addItem(data)
        data = ListTop10Dao(R.drawable.ic_launcher_foreground, "책 이름", 55, 4)
        listTop10Adapter?.addItem(data)
        data = ListTop10Dao(R.drawable.ic_launcher_foreground, "책 이름", 65, 5)
        listTop10Adapter?.addItem(data)
        data = ListTop10Dao(R.drawable.ic_launcher_foreground, "책 이름", 75, 6)
        listTop10Adapter?.addItem(data)
        data = ListTop10Dao(R.drawable.ic_launcher_foreground, "책 이름", 85, 7)
        listTop10Adapter?.addItem(data)
        data = ListTop10Dao(R.drawable.ic_launcher_foreground, "책 이름", 95, 8)
        listTop10Adapter?.addItem(data)
        data = ListTop10Dao(R.drawable.ic_launcher_foreground, "책 이름", 105, 9)
        listTop10Adapter?.addItem(data)
        data = ListTop10Dao(R.drawable.ic_launcher_foreground, "책 이름", 115, 10)
        listTop10Adapter?.addItem(data)
        data = ListTop10Dao(R.drawable.ic_launcher_foreground, "책 이름", 125, 11)
        listTop10Adapter?.addItem(data)
        data = ListTop10Dao(R.drawable.ic_launcher_foreground, "책 이름", 135, 12)
        listTop10Adapter?.addItem(data)
        data = ListTop10Dao(R.drawable.ic_launcher_foreground, "책 이름", 145, 13)
        listTop10Adapter?.addItem(data)
        data = ListTop10Dao(R.drawable.ic_launcher_foreground, "책 이름", 145, 13)
        listTop10Adapter?.addItem(data)

    }
}