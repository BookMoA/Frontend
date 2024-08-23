package com.bookmoa.android.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bookmoa.android.search.SearchAuthorFragment
import com.bookmoa.android.search.SearchBookListFragment
import com.bookmoa.android.search.SearchMemoFragment
import com.bookmoa.android.search.SearchTitleFragment

class SearchVpAdapter (fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SearchTitleFragment()
            1 -> SearchAuthorFragment()
            2 -> SearchBookListFragment()
            3 -> SearchMemoFragment()
            else -> throw IllegalArgumentException("Invalid position $position")
        }
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun containsItem(itemId: Long): Boolean {
        return itemId in 0 until itemCount
    }
}