package com.bookmoa.android.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.bookmoa.android.R
import com.bookmoa.android.adapter.OnboardingVPAdapter
import com.bookmoa.android.auth.LoginActivity
import com.bookmoa.android.databinding.ActivityOnboardingBinding
import com.bookmoa.android.mypage.MemberInfoFragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton.OnChangedCallback
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingActivity : AppCompatActivity() {
    lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startBtn.setOnClickListener {
            val intent = Intent(this@OnboardingActivity, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.skipBtn.setOnClickListener {
            binding.onboardingVp.currentItem = 3
        }

        val onBoardingVPAdapter = OnboardingVPAdapter(this)
        binding.onboardingVp.adapter = onBoardingVPAdapter

        TabLayoutMediator(binding.onboardingTl, binding.onboardingVp) { tab, position ->
            tab.customView = LayoutInflater.from(this).inflate(R.layout.onboarding_tab, null)
        }.attach()

        binding.onboardingTl.getTabAt(0)?.customView?.findViewById<ImageView>(R.id.tabIndicator)
            ?.setImageResource(R.drawable.tab_onboarding)

        binding.onboardingTl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<ImageView>(R.id.tabIndicator)?.setImageResource(R.drawable.tab_onboarding)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.findViewById<ImageView>(R.id.tabIndicator)?.setImageResource(R.drawable.tab_onboarding_default)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // 각 탭의 패딩 조정
        binding.onboardingTl.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.customView?.setPadding(8, 0, 8, 0) // 원하는 패딩 값 설정
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.customView?.setPadding(8, 0, 8, 0) // 원하는 패딩 값 설정
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // 페이지 전환 콜백
        binding.onboardingVp.registerOnPageChangeCallback(object: OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateUIForPage(position)
            }
        })
    }

    private fun updateUIForPage(position: Int) {
        when (position) {
            0, 1, 2 -> {
                binding.root.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
                binding.skipBtn.visibility = View.VISIBLE
                binding.startBtn.visibility = View.GONE
            }
            3 -> {
                binding.root.setBackgroundColor(ContextCompat.getColor(this, R.color.sub_color_1))
                binding.skipBtn.visibility = View.GONE
                binding.startBtn.visibility = View.VISIBLE
            }
        }
    }
}

