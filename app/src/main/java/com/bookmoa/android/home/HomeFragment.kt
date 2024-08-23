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
import com.bookmoa.android.R
import com.bookmoa.android.databinding.FragmentHomeBinding
import com.bookmoa.android.search.SearchFragment

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var isTracking = false
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

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
