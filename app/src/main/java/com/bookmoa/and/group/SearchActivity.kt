package com.bookmoa.and.group

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bookmoa.and.databinding.ActivitySearchBinding
import com.bookmoa.bookmoa.GroupActivity
import com.google.android.material.tabs.TabLayoutMediator

class SearchActivity: AppCompatActivity() {
    private val binding: ActivitySearchBinding by lazy {
        ActivitySearchBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupSearchView()

        binding.searchBackIv.setOnClickListener {
            val intent = Intent(this, GroupActivity::class.java)
            startActivity(intent)
        }

        val adapter = BookClubSearchViewPagerAdapter(this)
        adapter.addFragment(SearchNameFragment(), "모임명")
        adapter.addFragment(SearchNoticeFragment(), "공지사항")

        binding.searchVp.adapter = adapter

        TabLayoutMediator(binding.searchTl, binding.searchVp) {tab, position ->
            tab.text = when (position) {
                0 -> "모임명"
                1 -> "공지사항"
                else -> null
            }
        }.attach()
    }

    private fun setupSearchView() {
        binding.searchSv.apply {
            queryHint = "검색어를 입력하세요"
            setIconifiedByDefault(false)
            isSubmitButtonEnabled = false

            val searchEditText = findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            searchEditText.apply {
                setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
                setHintTextColor(Color.parseColor("#999999"))
                setTextColor(Color.BLACK)
            }
        }
    }
}
class BookClubSearchViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val fragmentList = mutableListOf<Fragment>()
    private val fragmentTitleList = mutableListOf<String>()

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}