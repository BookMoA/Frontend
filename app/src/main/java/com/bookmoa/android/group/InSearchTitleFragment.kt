package com.bookmoa.android.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.R
import com.bookmoa.android.adapter.InSearchTitleFragmentAdapter
import com.bookmoa.android.adapter.InSearchTitleItems
import com.bookmoa.android.databinding.FragmentInsearchtitlevpBinding

class InSearchTitleFragment: Fragment() {
    private var _binding: FragmentInsearchtitlevpBinding? = null
    private val binding get() = _binding!!

    private lateinit var titleAdapter: InSearchTitleFragmentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentInsearchtitlevpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        binding.insearchtitleRv.layoutManager = LinearLayoutManager(context)

        val titleItems = listOf(
            InSearchTitleItems("i","i","i", R.drawable.background_recgrey),
            InSearchTitleItems("i","i","i", R.drawable.background_recgrey),
            InSearchTitleItems("i","i","i", R.drawable.background_recgrey),
            InSearchTitleItems("i","i","i", R.drawable.background_recgrey),
            InSearchTitleItems("i","i","i", R.drawable.background_recgrey),
        )

        titleAdapter = InSearchTitleFragmentAdapter(titleItems)
        binding.insearchtitleRv.adapter = titleAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}