package com.bookmoa.android.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.MainActivity
import com.bookmoa.android.R
import com.bookmoa.android.adapter.ListDetailAdapter

import com.bookmoa.android.databinding.FragmentListDetailBinding

class ListDetailFragment : Fragment() {
    lateinit var binding: FragmentListDetailBinding
    private var itemListDetailAdapter: ListDetailAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListDetailBinding.inflate(inflater, container, false)

        binding.listDetailBackIcon.setOnClickListener {
            (activity as MainActivity).switchFragment(StudyFragment())

        }
        binding.listDetailEditBtn.setOnClickListener {
            (activity as MainActivity).switchFragment(ListDetailEditFragment())
        }

        itemListDetailAdapter = ListDetailAdapter()

        // RecyclerView에 레이아웃 매니저 설정
        binding.listDetailRv.layoutManager = LinearLayoutManager(context)

        // RecyclerView에 어댑터 설정
        binding.listDetailRv.adapter = itemListDetailAdapter

        // 임의의 데이터 어댑터에 추가
        getItem()
        return binding.root
    }

    private fun getItem() {
        var data = ItemListDetailDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        itemListDetailAdapter?.addItem(data)
        data = ItemListDetailDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        itemListDetailAdapter?.addItem(data)
        data = ItemListDetailDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        itemListDetailAdapter?.addItem(data)
        data = ItemListDetailDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        itemListDetailAdapter?.addItem(data)
        data = ItemListDetailDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        itemListDetailAdapter?.addItem(data)
        data = ItemListDetailDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        itemListDetailAdapter?.addItem(data)

    }
}