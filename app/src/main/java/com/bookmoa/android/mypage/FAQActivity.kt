package com.bookmoa.android.mypage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.bookmoa.android.R
import com.bookmoa.android.databinding.ActivityFaqBinding
import com.bookmoa.android.mypage.AllFAQFragment
import com.google.android.material.tabs.TabLayout

class FAQActivity : AppCompatActivity() {

    lateinit var binding : ActivityFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backIc.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.FAQTl.addTab(binding.FAQTl.newTab().setText("전체"))
        binding.FAQTl.addTab(binding.FAQTl.newTab().setText("이용안내"))
        binding.FAQTl.addTab(binding.FAQTl.newTab().setText("회원정보"))

        replaceFragment(AllFAQFragment())

        binding.FAQTl.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment: Fragment = when (tab.position) {
                    0 -> AllFAQFragment()
                    1 -> UseInfoFAQFragment()
                    2 -> UserInfoFragment()
                    else -> AllFAQFragment()
                }
                replaceFragment(fragment)

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.tab_content, fragment)
        fragmentTransaction.commit()
    }
}