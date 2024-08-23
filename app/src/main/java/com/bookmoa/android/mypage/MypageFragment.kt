package com.bookmoa.android.mypage

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bookmoa.android.R
import com.bookmoa.android.auth.LoginActivity
import com.bookmoa.android.auth.OnboardingActivity
import com.bookmoa.android.databinding.FragmentMypageBinding
import com.bookmoa.android.services.UserInfoManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Member

class MypageFragment : Fragment() {

    lateinit var binding: FragmentMypageBinding
    private lateinit var userInfoManager: UserInfoManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        userInfoManager = UserInfoManager(requireContext())

        GlobalScope.launch {
            val email = userInfoManager.getEmail()
            val nickname = userInfoManager.getNickname()

            Log.d("[MYPAGE]", "Email: $email, Nickname: $nickname")

            binding.nicknameTv.text = nickname
        }

        GlobalScope.launch {
            val group = userInfoManager.getGroup()

            binding.bookGroupNameTv.text = if (group.isNullOrEmpty()) {
                "독서그룹명"
            } else {
                group
            }
        }

        // 프로필 이미지 로드
        GlobalScope.launch {
            val profileImageUri = userInfoManager.getProfileImageUri()
            withContext(Dispatchers.Main) {
                if (profileImageUri != null && profileImageUri.isNotEmpty()) {
                    // Glide를 사용하여 URL에서 이미지 로드
                    Glide.with(requireContext())
                        .load(profileImageUri)
                        .placeholder(R.drawable.ic_profile_unfilled) // 기본 이미지
                        .error(R.drawable.ic_profile_unfilled) // 에러 시 기본 이미지
                        .into(binding.profileIv)
                } else {
                    // URI가 null이거나 비어있으면 기본 이미지로 설정
                    binding.profileIv.setImageResource(R.drawable.ic_profile_unfilled)
                }
            }
        }

        // 프로필 변경
        binding.changeProfileBtn.setOnClickListener {
            val changeProfileFragment = ChangeProfileFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, changeProfileFragment)
                .addToBackStack(null)
                .commit()
        }

        // 알림 설정
        binding.noticeSettingBtn.setOnClickListener {
            val notificationFragment = NotificationFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, notificationFragment)
                .addToBackStack(null) // 뒤로 가기 버튼을 누르면 MypageFragment로 돌아오도록 함
                .commit()
        }

        // 로그아웃
        binding.logoutBtn.setOnClickListener {
            showLogoutDialog()
        }

        // 자주 묻는 질문
        binding.FAQBtn.setOnClickListener {
            val intent = Intent(requireContext(), FAQActivity::class.java)
            startActivity(intent)
        }

        // 문의하기
        binding.inquiryBtn.setOnClickListener {
            // 구글폼 연결
        }

        binding.ratingBtn.setOnClickListener {
            // 플레이스토어 연결
        }

        binding.introMoaBtn.setOnClickListener {

            val MemberInfoFragment = MemberInfoFragment()
            parentFragmentManager.beginTransaction()
                .replace(R.id.main_frm, MemberInfoFragment)
                .addToBackStack(null) // 뒤로 가기 버튼을 누르면 MypageFragment로 돌아오도록 함
                .commit()
        }

        binding.serviceTermsBtn.setOnClickListener {
            // 이용약관 연결
        }

        binding.privacyPolicyBtn.setOnClickListener {
            // 개인정보처리방침 연결
        }

        return binding.root
    }

    private fun showLogoutDialog() {
        if (isAdded) {
            val dialogView = layoutInflater.inflate(R.layout.logout_alert_dialog, null)
            val dialogBuilder = AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .create()

            val cancelButton = dialogView.findViewById<Button>(R.id.cancel_btn)
            val confirmButton = dialogView.findViewById<Button>(R.id.confirm_btn)

            cancelButton.setOnClickListener {
                dialogBuilder.dismiss()
            }
            confirmButton.setOnClickListener {
                logout()
            }

            dialogBuilder.show()
        }
    }

    private fun logout() {
        GlobalScope.launch {
            userInfoManager.updateTokens("", "", "", "")
            userInfoManager.saveProfileImageUri("")
            val intent = Intent(requireContext(), OnboardingActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
    }
}