package com.bookmoa.android.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.R
import com.bookmoa.android.adapter.InSearchAuthorFragmentAdapter
import com.bookmoa.android.adapter.InSearchAuthorItems
import com.bookmoa.android.databinding.FragmentInsearchauthorvpBinding

class InSearchAuthorFragment: Fragment() {
    private var _binding: FragmentInsearchauthorvpBinding? = null
    private val binding get() = _binding!!

    private lateinit var authorAdapter: InSearchAuthorFragmentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentInsearchauthorvpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.insearchauthorRv.layoutManager = LinearLayoutManager(context)

        val authorItems = listOf(
            InSearchAuthorItems("bye","bye","bye", R.drawable.background_recgrey),
            InSearchAuthorItems("bye","bye","bye", R.drawable.background_recgrey),
            InSearchAuthorItems("bye","bye","bye", R.drawable.background_recgrey),
            InSearchAuthorItems("bye","bye","bye", R.drawable.background_recgrey),
            InSearchAuthorItems("bye","bye","bye", R.drawable.background_recgrey),
            )

        authorAdapter = InSearchAuthorFragmentAdapter(authorItems)
        binding.insearchauthorRv.adapter = authorAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}