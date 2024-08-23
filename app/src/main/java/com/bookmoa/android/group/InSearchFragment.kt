package com.bookmoa.android.group

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bookmoa.android.databinding.FragmentInsearchBinding
import com.google.android.material.tabs.TabLayoutMediator

class InSearchFragment : Fragment() {
    private var _binding: FragmentInsearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInsearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSearchView()

        binding.insearchBackIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        val adapter = BookClubInSearchViewPagerAdapter(requireActivity())
        adapter.addFragment(InSearchArticleFragment(), "글+댓글")
        adapter.addFragment(InSearchTitleFragment(), "제목")
        adapter.addFragment(InSearchAuthorFragment(), "작성자")

        binding.insearchVp.adapter = adapter

        TabLayoutMediator(binding.insearchTl, binding.insearchVp) { tab, position ->
            tab.text = when (position) {
                0 -> "글+댓글"
                1 -> "제목"
                2 -> "작성자"
                else -> null
            }
        }.attach()
    }

    private fun setupSearchView() {
        binding.insearchSv.apply {
            queryHint = "검색어를 입력하세요"
            setIconifiedByDefault(false)
            isSubmitButtonEnabled = false

            val searchEditText = findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            searchEditText.apply {
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                setHintTextColor(Color.parseColor("#999999"))
                setTextColor(Color.BLACK)
            }
            val searchPlate = findViewById<View>(androidx.appcompat.R.id.search_plate)
            searchPlate.setBackgroundColor(Color.TRANSPARENT)

            val submitArea = findViewById<View>(androidx.appcompat.R.id.submit_area)
            submitArea.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class BookClubInSearchViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val fragmentList = mutableListOf<Fragment>()
    private val fragmentTitleList = mutableListOf<String>()

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}