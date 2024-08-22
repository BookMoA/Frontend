package com.bookmoa.android.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bookmoa.android.search.SearchAuthorFragment
import com.bookmoa.android.search.SearchBookListFragment
import com.bookmoa.android.search.SearchMemoFragment
import com.bookmoa.android.search.SearchTitleFragment
import com.bookmoa.android.search.SearchTotalFragment

class SearchVpAdapter (fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SearchTotalFragment()
            1 -> SearchTitleFragment()
            2 -> SearchAuthorFragment()
            3 -> SearchBookListFragment()
            4 -> SearchMemoFragment()
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