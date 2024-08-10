package com.bookmoa.android.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bookmoa.android.study.CurrentlyReadingFragment
import com.bookmoa.android.study.FinishedBooksFragment
import com.bookmoa.android.study.TotalBooksFragment
import com.bookmoa.android.study.MyListFragment

class MyStorageVpAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> TotalBooksFragment()
            1 -> FinishedBooksFragment()
            2 -> CurrentlyReadingFragment()
            else -> MyListFragment()
        }
    }
}