package com.bookmoa.android.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bookmoa.android.R
import com.bookmoa.android.adapter.OnboardingVPAdapter
import com.bookmoa.android.auth.LoginActivity
import com.bookmoa.android.databinding.ActivityOnboardingBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class OnboardingActivity : AppCompatActivity() {
    lateinit var binding: ActivityOnboardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onBoardingVPAdapter = OnboardingVPAdapter(this)
        binding.onboardingVp.adapter = onBoardingVPAdapter

        TabLayoutMediator(binding.onboardingTl, binding.onboardingVp) { tab, position ->
            tab.customView = LayoutInflater.from(this).inflate(R.layout.onboarding_tab, null)
        }.attach()

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

        /*
        binding.onboardingTl.apply {
            post {
                for (i in 0 until tabCount) {
                    val tab = (getChildAt(0) as ViewGroup).getChildAt(i) as ViewGroup
                    val layoutParams = tab.layoutParams as ViewGroup.MarginLayoutParams
                    layoutParams.setMargins(0, 0, 7, 0)
                    tab.requestLayout()
                }
            }

         */
            binding.loginBtn.setOnClickListener {
                // 로그인 페이지
                val intent = Intent(this@OnboardingActivity, LoginActivity::class.java)
                startActivity(intent)
            }

        }
    }

