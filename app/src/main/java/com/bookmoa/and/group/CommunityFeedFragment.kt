package com.bookmoa.and.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.and.R
import com.bookmoa.and.adapter.CommunityFeedFragmentAdapter
import com.bookmoa.and.adapter.CommunityFeedItems
import com.bookmoa.and.databinding.FragmentCommunityfeedvpBinding

class CommunityFeedFragment: Fragment() {
    private var _binding: FragmentCommunityfeedvpBinding? = null
    private val binding get() = _binding!!

    private lateinit var feedAdapter: CommunityFeedFragmentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCommunityfeedvpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.communityfeedRv.layoutManager = LinearLayoutManager(context)

        val feedItems = listOf(
            CommunityFeedItems(R.drawable.icon_profile, "닉네임", "2024년 5월 19일", "함께 독서 챌린지 하실 분 찾습니다!!\n", "• 기간: 7-8월\n• 인증 방법: 독서 사진을 댓글로 남기기\n• 3일 이상 미인증 시 챌린지 박탈입니다\n\n함께 하고 싶은 분들은 댓글 달아주세요!"),
            CommunityFeedItems(R.drawable.icon_profile, "닉네임", "2024년 5월 19일", "함께 독서 챌린지 하실 분 찾습니다!!\n", "• 기간: 7-8월\n• 인증 방법: 독서 사진을 댓글로 남기기\n• 3일 이상 미인증 시 챌린지 박탈입니다\n\n함께 하고 싶은 분들은 댓글 달아주세요!")
        )

        feedAdapter = CommunityFeedFragmentAdapter(feedItems)
        binding.communityfeedRv.adapter = feedAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}