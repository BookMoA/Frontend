package com.bookmoa.android

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bookmoa.android.auth.LoginActivity
import com.bookmoa.android.auth.OnboardingActivity
import com.bookmoa.android.databinding.ActivityMainBinding
import com.bookmoa.android.group.GroupFragment
import com.bookmoa.android.home.HomeFragment
import com.bookmoa.android.memo.BookMemoFragment
import com.bookmoa.android.mypage.MypageFragment
import com.bookmoa.android.services.UserInfoManager
import com.bookmoa.android.study.StudyFragment
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    //testtt
    private lateinit var binding: ActivityMainBinding
    private lateinit var userInfoManager: UserInfoManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        // installSplashScreen()

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)



        setContentView(binding.root)

        // 로그인으로 이동
        userInfoManager = UserInfoManager(applicationContext)
        GlobalScope.launch {
            Log.d("[PRINT/TOKEN]", "${userInfoManager.getTokens()}")
            if (!userInfoManager.isValidToken()) {
                Log.d("[PRINT/TOKEN]", "유효하지 않은 토큰")
                val intent = Intent(this@MainActivity, OnboardingActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                startActivity(intent)
            } else {
                initBottomNavigation()
            }
        }

        initBottomNavigation()

    }

    fun switchFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.main_frm, fragment)
        fragmentTransaction.addToBackStack(null) // 뒤로 가기 버튼으로 이전 Fragment로 돌아가기 가능
        fragmentTransaction.commit()
    }


    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }


    private fun initBottomNavigation() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.memoFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, BookMemoFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.studyFragment -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, StudyFragment())
                            .commitAllowingStateLoss()
                        return@setOnItemSelectedListener true
                }

                R.id.bookClubFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, GroupFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.setupFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, MypageFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}