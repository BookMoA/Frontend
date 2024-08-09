package com.bookmoa.and.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.and.R
import com.bookmoa.and.adapter.InSearchArticleFragmentAdapter
import com.bookmoa.and.adapter.InSearchArticleItems
import com.bookmoa.and.databinding.FragmentInsearcharticlevpBinding

class InSearchArticleFragment: Fragment() {
    private var _binding: FragmentInsearcharticlevpBinding? = null
    private val binding get() = _binding!!

    private lateinit var articleAdapter: InSearchArticleFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInsearcharticlevpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.insearcharticleRv.layoutManager = LinearLayoutManager(context)

        val articleItems = listOf(
            InSearchArticleItems("hi","hi","hi", R.drawable.background_recgrey),
            InSearchArticleItems("hi","hi","hi",R.drawable.background_recgrey),
            InSearchArticleItems("hi","hi","hi",R.drawable.background_recgrey),
            InSearchArticleItems("hi","hi","hi",R.drawable.background_recgrey)
        )

        articleAdapter = InSearchArticleFragmentAdapter(articleItems)
        binding.insearcharticleRv.adapter = articleAdapter
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}