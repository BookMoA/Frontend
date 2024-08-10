package com.bookmoa.android.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bookmoa.android.mypage.AllFAQFragment
import com.bookmoa.android.mypage.UseInfoFAQFragment
import com.bookmoa.android.mypage.UserInfoFragment

class FAQAdapter (fragment: FragmentActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllFAQFragment()
            1 -> UseInfoFAQFragment()
            else -> UserInfoFragment()
        }
    }
}