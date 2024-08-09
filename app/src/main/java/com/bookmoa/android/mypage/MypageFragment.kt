package com.bookmoa.android.mypage

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.bookmoa.android.R
import com.bookmoa.android.databinding.FragmentMypageBinding
import com.bookmoa.android.mypage.ChangeProfileActivity
import com.bookmoa.android.mypage.FAQActivity
import com.bookmoa.android.mypage.IntroMoaActivity

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class MypageFragment : Fragment() {

    lateinit var binding: FragmentMypageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(inflater, container, false)

        // 프로필 변경
        binding.changeProfileBtn.setOnClickListener {
            val intent = Intent(requireContext(), ChangeProfileActivity::class.java)
            startActivity(intent)
        }

        // 알림 설정
        binding.noticeSettingBtn.setOnClickListener {
            // 알림 설정 페이지 이동
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
            val intent = Intent(requireContext(), IntroMoaActivity::class.java)
            startActivity(intent)
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
            //로그아웃 처리
            dialogBuilder.dismiss()
        }

        dialogBuilder.show()
    }
}