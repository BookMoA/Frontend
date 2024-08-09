package com.bookmoa.android.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bookmoa.android.R
import com.bookmoa.android.adapter.StorageBookAdapter
import com.bookmoa.android.databinding.FragmentFinishedBooksBinding

class FinishedBooksFragment : Fragment() {
    lateinit var binding: FragmentFinishedBooksBinding
    private var storageRVAdapter: StorageBookAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFinishedBooksBinding.inflate(inflater, container, false)

        // 어댑터 초기화
        storageRVAdapter = StorageBookAdapter()

        // RecyclerView에 레이아웃 매니저 설정
        binding.finishedBooksRvList.layoutManager = GridLayoutManager(context, 3)

        // RecyclerView에 어댑터 설정
        binding.finishedBooksRvList.adapter = storageRVAdapter

        // 임의의 데이터를 어댑터에 추가
        getItem()

        return binding.root
    }

    private fun getItem() {
        var data = GridBookDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        storageRVAdapter?.addItem(data)
        data = GridBookDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        storageRVAdapter?.addItem(data)
        data = GridBookDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        storageRVAdapter?.addItem(data)
        data = GridBookDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        storageRVAdapter?.addItem(data)
        data = GridBookDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        storageRVAdapter?.addItem(data)
        data = GridBookDao(R.drawable.ic_launcher_foreground, "책 이름", "지은이")
        storageRVAdapter?.addItem(data)
    }
}