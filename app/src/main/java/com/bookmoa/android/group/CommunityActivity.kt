package com.bookmoa.android.group

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bookmoa.android.databinding.ActivityCommunityBinding
import com.bookmoa.bookmoa.GroupActivity
import com.google.android.material.tabs.TabLayoutMediator

class CommunityActivity: AppCompatActivity() {
    private val binding: ActivityCommunityBinding by lazy { ActivityCommunityBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.communityBackIv.setOnClickListener {
            val intent = Intent(this, GroupActivity::class.java)
            startActivity(intent)
        }

        binding.communitySearchIv.setOnClickListener {
            val intent = Intent(this, InSearchActivity::class.java)
            startActivity(intent)
        }

        binding.communityWriteIv.setOnClickListener{
            val intent = Intent(this, WriteActivity::class.java)
            startActivity(intent)
        }

        val adapter = ViewPagerAdapter(this)
        adapter.addFragment(CommunityFeedFragment(), "피드")
        adapter.addFragment(CommunityStatisticFragment(), "통계")
        adapter.addFragment(CommunityMemberFragment(), "멤버")
        adapter.addFragment(CommunityManageFragment(), "관리")

        binding.communityVp.adapter = adapter

        TabLayoutMediator(binding.communityTl, binding.communityVp) {tab, position ->
            tab.text = when (position) {
                0 -> "피드"
                1 -> "통계"
                2 -> "멤버"
                3 -> "관리"
                else -> null
            }
        }.attach()
    }
}

class ViewPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private val fragmentList = mutableListOf<Fragment>()
    private val fragmentTitleList = mutableListOf<String>()

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}