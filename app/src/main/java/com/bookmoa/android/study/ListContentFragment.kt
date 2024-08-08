package com.bookmoa.android.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.MainActivity
import com.bookmoa.android.R
import com.bookmoa.android.adapter.ListContentAdapter
import com.bookmoa.android.databinding.FragmentListContentBinding

class ListContentFragment : Fragment() {

    lateinit var binding: FragmentListContentBinding
    private var itemListContentAdapter: ListContentAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListContentBinding.inflate(inflater, container, false)
        binding.listContentBackIcon.setOnClickListener {
            (activity as MainActivity).switchFragment(StudyFragment())

        }

        itemListContentAdapter = ListContentAdapter()

        // RecyclerView에 레이아웃 매니저 설정
        binding.listContentRvList.layoutManager = LinearLayoutManager(context)

        // RecyclerView에 어댑터 설정
        binding.listContentRvList.adapter = itemListContentAdapter


        // 임의의 데이터 어댑터에 추가
        getItem()


        return binding.root
    }

    private fun getItem() {
        var data = ItemListContentDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        itemListContentAdapter?.addItem(data)
        data = ItemListContentDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        itemListContentAdapter?.addItem(data)
        data = ItemListContentDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        itemListContentAdapter?.addItem(data)
        data = ItemListContentDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        itemListContentAdapter?.addItem(data)
        data = ItemListContentDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        itemListContentAdapter?.addItem(data)
        data = ItemListContentDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        itemListContentAdapter?.addItem(data)

    }
}