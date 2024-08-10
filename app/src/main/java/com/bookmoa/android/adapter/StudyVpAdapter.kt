package com.bookmoa.android.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bookmoa.android.study.StudyVp1Fragment
import com.bookmoa.android.study.StudyVp2Fragment
import com.bookmoa.android.study.StudyVp3Fragment
import com.bookmoa.android.study.StudyVp4Fragment
import com.bookmoa.android.study.StudyVp5Fragment

class StudyVpAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int =5

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> StudyVp1Fragment()
            1 -> StudyVp2Fragment()
            2 -> StudyVp3Fragment()
            3 -> StudyVp4Fragment()
            else -> StudyVp5Fragment()
        }
    }
}
