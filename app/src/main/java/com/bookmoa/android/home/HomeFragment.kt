package com.bookmoa.android.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.bookmoa.android.R
import com.bookmoa.android.databinding.FragmentHomeBinding
import com.bookmoa.android.search.SearchFragment
import com.bookmoa.android.services.UserInfoManager
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var isTracking = false
    private val handler = Handler(Looper.getMainLooper())

    private lateinit var userInfoManager: UserInfoManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        userInfoManager = UserInfoManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            val email = userInfoManager.getEmail()
            val nickname = userInfoManager.getNickname()

            Log.d("[HOME]", "Email: $email, Nickname: $nickname")

            withContext(Dispatchers.Main) {
                if (isAdded) {  // Fragment가 Context에 연결되어 있는지 확인
                    binding.textView8.text = nickname
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val group = userInfoManager.getGroup()
            val totalPage = userInfoManager.getTotalPage()

            withContext(Dispatchers.Main) {
                if (isAdded) {  // Fragment가 Context에 연결되어 있는지 확인
                    binding.textView9.text = if (group.isNullOrEmpty()) {
                        "독서그룹명"
                    } else {
                        group
                    }

                    binding.textView14.text = if (totalPage == null || totalPage == 0) {
                        "0p"
                    } else {
                        totalPage.toString() + "p"
                    }
                }
            }
        }

        // 프로필 이미지 로드
        viewLifecycleOwner.lifecycleScope.launch {
            val profileImageUri = userInfoManager.getProfileImageUri()
            withContext(Dispatchers.Main) {
                if (isAdded) {  // Fragment가 Context에 연결되어 있는지 확인
                    if (profileImageUri != null && profileImageUri.isNotEmpty()) {
                        Glide.with(requireContext())
                            .load(profileImageUri)
                            .placeholder(R.drawable.ic_profile_unfilled)
                            .error(R.drawable.ic_profile_unfilled)
                            .into(binding.imageView16)
                    } else {
                        binding.imageView16.setImageResource(R.drawable.ic_profile_unfilled)
                    }
                }
            }
        }



        // 독서통장 페이지로 이동
        binding.user.setOnClickListener {
            val intent = Intent(requireContext(), PassbookActivity::class.java)
            startActivity(intent)
        }

        // 밀어서 독서 집중 모드 기능 구현
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // seekbar 작동하는지 확인
                Log.d("HomeFragment", "SeekBar touch started")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                handler.removeCallbacksAndMessages(null)

                if (!isTracking) {
                    isTracking = true
                    handler.postDelayed({
                        val focusmodeIntent = Intent(requireContext(), FocusmodeActivity::class.java)
                        startActivity(focusmodeIntent)
                    }, 1000)
                }
            }
        })

        // 검색 화면으로 이동
        binding.imgAddBook1.setOnClickListener {
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.main_frm, SearchFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        handler.removeCallbacksAndMessages(null)
    }
}
