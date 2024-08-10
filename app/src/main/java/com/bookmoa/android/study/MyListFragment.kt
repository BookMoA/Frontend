package com.bookmoa.android.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.R
import com.bookmoa.android.adapter.StorageListAdapter
import com.bookmoa.android.databinding.FragmentMyListBinding

class MyListFragment : Fragment() {
    lateinit var binding: FragmentMyListBinding
    private var storageRVAdapter: StorageListAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyListBinding.inflate(inflater, container, false)

        // 어댑터 초기화
        storageRVAdapter = StorageListAdapter()

        // RecyclerView에 레이아웃 매니저 설정
        binding.wantToReadRv.layoutManager = LinearLayoutManager(context)

        // RecyclerView에 어댑터 설정
        binding.wantToReadRv.adapter = storageRVAdapter

        // 임의의 데이터를 어댑터에 추가
        getItem()

        return binding.root
    }

    private fun getItem() {
        var data = StorageListDao(R.drawable.ic_launcher_foreground, "책 이름", 45)
        storageRVAdapter?.addItem(data)
        data = StorageListDao(R.drawable.ic_launcher_foreground, "책 이름", 55)
        storageRVAdapter?.addItem(data)
        data = StorageListDao(R.drawable.ic_launcher_foreground, "책 이름", 65)
        storageRVAdapter?.addItem(data)
        data = StorageListDao(R.drawable.ic_launcher_foreground, "책 이름", 75)
        storageRVAdapter?.addItem(data)
        data = StorageListDao(R.drawable.ic_launcher_foreground, "책 이름", 85)
        storageRVAdapter?.addItem(data)
        data = StorageListDao(R.drawable.ic_launcher_foreground, "책 이름", 95)
        storageRVAdapter?.addItem(data)
        data = StorageListDao(R.drawable.ic_launcher_foreground, "책 이름", 105)
        storageRVAdapter?.addItem(data)
        data = StorageListDao(R.drawable.ic_launcher_foreground, "책 이름", 115)
        storageRVAdapter?.addItem(data)

    }
}