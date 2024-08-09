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
import com.bookmoa.and.databinding.ActivityInsearchBinding
import com.google.android.material.tabs.TabLayoutMediator

class InSearchActivity: AppCompatActivity() {
    private val binding: ActivityInsearchBinding by lazy{
        ActivityInsearchBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupSearchView()

        binding.insearchBackIv.setOnClickListener{
            val intent = Intent(this, CommunityActivity::class.java)
            startActivity(intent)
        }

        val adapter = BookClubInSearchViewPagerAdapter(this)
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
        }
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