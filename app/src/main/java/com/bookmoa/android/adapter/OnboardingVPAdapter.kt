package com.bookmoa.android.adapter
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bookmoa.android.auth.OnboardingActivity
import com.bookmoa.android.auth.OnboardingTab1Fragment
import com.bookmoa.android.auth.OnboardingTab2Fragment
import com.bookmoa.android.auth.OnboardingTab3Fragment
import com.bookmoa.android.auth.OnboardingTab4Fragment

class OnboardingVPAdapter(activity: OnboardingActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnboardingTab1Fragment()
            1 -> OnboardingTab2Fragment()
            2 -> OnboardingTab3Fragment()
            3 -> OnboardingTab4Fragment()
            else -> throw IllegalStateException("Unexpected position $position")
        }
    }

}