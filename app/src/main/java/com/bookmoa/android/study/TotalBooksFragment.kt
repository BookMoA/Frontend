package com.bookmoa.android.study

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bookmoa.android.R
import com.bookmoa.android.adapter.StorageBookAdapter
import com.bookmoa.android.databinding.FragmentTotalBooksBinding


class TotalBooksFragment : Fragment() {
    lateinit var binding: FragmentTotalBooksBinding
    private var storageRVAdapter: StorageBookAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTotalBooksBinding.inflate(inflater, container, false)


        storageRVAdapter = StorageBookAdapter()
        binding.totalBookRvList.layoutManager = GridLayoutManager(context, 3)
        binding.totalBookRvList.adapter = storageRVAdapter
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