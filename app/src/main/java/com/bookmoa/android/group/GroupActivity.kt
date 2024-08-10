package com.bookmoa.bookmoa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bookmoa.android.databinding.ActivityGroupBinding
import com.bookmoa.android.group.GroupRvFragment
import com.bookmoa.android.group.MakeActivity
import com.bookmoa.android.group.SearchActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class GroupActivity : AppCompatActivity() {

    val binding by lazy{
        ActivityGroupBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.groupSearchIv.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

        binding.groupMakeFl.setOnClickListener {
            val intent = Intent(this, MakeActivity::class.java)
            startActivity(intent)
        }

        val viewPager: ViewPager2 = binding.groupVp
        val tabLayout: TabLayout = binding.groupTl

        val adapter = BookClubVP2Adapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "신규"
                1 -> "활동순"
                2 -> "마감 임박"
                else -> null
            }
        }.attach()
    }
}

class BookClubVP2Adapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return GroupRvFragment.newInstance(position)
    }
}