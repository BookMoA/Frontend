package com.bookmoa.android.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.R
import com.bookmoa.android.adapter.InSearchArticleFragmentAdapter
import com.bookmoa.android.adapter.InSearchArticleItems
import com.bookmoa.android.databinding.FragmentInsearcharticlevpBinding

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

        val spinner = binding.insearcharticleSpinner
        val spinnerContainer = binding.insearcharticleContainer
        val originalItems = resources.getStringArray(R.array.spinner_options).toList()
        val items = mutableListOf<String>().apply {
            add("정확도순")
            addAll(originalItems)
        }

        val adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            items
        ) {
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.textSize = 14f

                return view
            }

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textView = view.findViewById<TextView>(android.R.id.text1)
                textView.text = items[0]
                textView.textSize = 14f
                return view
            }
        }

        spinner.adapter = adapter

        spinnerContainer.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                spinnerContainer.viewTreeObserver.removeOnGlobalLayoutListener(this)
                val width = spinner.width
                spinnerContainer.layoutParams.width = width
                spinner.requestLayout()
            }
        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                if (position > 0) { // 첫 번째 항목(현재 선택된 항목)이 아닌 경우에만 변경
                    val selectedItem = items[position]
                    items[0] = selectedItem
                    adapter.notifyDataSetChanged()
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        spinner.setSelection(items.indexOf("정확도순"))
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}