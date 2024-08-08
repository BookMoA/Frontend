package com.bookmoa.android.study

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.MainActivity
import com.bookmoa.android.R
import com.bookmoa.android.adapter.StudyVpAdapter
import com.bookmoa.android.databinding.FragmentStudyBinding
import com.bookmoa.android.search.SearchFragment
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate


class StudyFragment : Fragment() {

    private val timer = Timer()
    private val handler = Handler(Looper.getMainLooper())
    lateinit var binding: FragmentStudyBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyBinding.inflate(inflater, container, false)

        binding.studyMyStorageIcon.setOnClickListener {
            (activity as MainActivity).switchFragment(MyStorageFragment())
        }

        binding.studyListChartIcon.setOnClickListener {
            (activity as MainActivity).switchFragment(ListTop10Fragment())
        }

        binding.myStorageFirstList.setOnClickListener {
            (activity as MainActivity).switchFragment(ListDetailFragment())
        }

        binding.studySearchIcon.setOnClickListener {
            (activity as MainActivity).switchFragment(SearchFragment())
        }
        val recommendAdapter = StudyVpAdapter(this)
        binding.studyRecommendVp.adapter = recommendAdapter
        binding.studyRecommendIndicator.setViewPager(binding.studyRecommendVp)


        val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin).toFloat()
        val pageOffsetPx = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()

        binding.studyRecommendVp.setPageTransformer { page, position ->
            val offset = position * -(2 * pageOffsetPx + pageMarginPx)
            page.translationX = offset
        }

        // ViewPager2 내부의 RecyclerView에 패딩을 추가하여 중앙 정렬 효과
        val recyclerView = binding.studyRecommendVp.getChildAt(0) as RecyclerView
        val padding = (pageMarginPx + pageOffsetPx).toInt()
        recyclerView.setPadding(padding, 0, padding, 0)
        recyclerView.clipToPadding = false

        binding.listChartFirstListImgCarview.setOnClickListener {
            (activity as MainActivity).switchFragment(ListContentFragment())
        }
        startAutoSlide(recommendAdapter)
        return binding.root
    }

    private fun startAutoSlide(adapter: StudyVpAdapter) {
        // 일정 간격으로 슬라이드 변경 (3초마다)
        timer.scheduleAtFixedRate(3000, 3000) {
            handler.post {
                val nextItem = binding.studyRecommendVp.currentItem + 1
                if (nextItem < adapter.itemCount) {
                    binding.studyRecommendVp.currentItem = nextItem
                } else {
                    binding.studyRecommendVp.currentItem = 0 // 마지막 페이지에서 첫 페이지로 순환
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer.cancel()
        timer?.cancel()
    }
}
