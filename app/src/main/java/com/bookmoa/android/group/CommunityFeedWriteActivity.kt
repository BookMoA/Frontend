package com.bookmoa.android.group

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.R
import com.bookmoa.android.adapter.CommunityFeedWriteAdapter
import com.bookmoa.android.adapter.CommunityFeedWriteItems
import com.bookmoa.android.databinding.ActivityCommunityfeedwriteBinding

class CommunityFeedWriteActivity: AppCompatActivity() {
    private val binding: ActivityCommunityfeedwriteBinding by lazy {
        ActivityCommunityfeedwriteBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: CommunityFeedWriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()

        binding.communityBackIv.setOnClickListener {
            finish()
        }
        binding.communitySearchIv.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val transaction = fragmentManager.beginTransaction()

            transaction.replace(R.id.communityfeedwrite_act, InSearchFragment())
            transaction.addToBackStack(null)
            transaction.commit()
        }

        val nickname = intent.getStringExtra("NICKNAME") ?: ""
        val description = intent.getStringExtra("DESCRIPTION") ?: ""
        val title = intent.getStringExtra("TITLE") ?: ""
        val date = intent.getStringExtra("DATE") ?: ""
        binding.communityfeedwriteNameTv.text = nickname
        binding.communityfeedwriteTitleTv.text = title
        binding.communityfeedwriteDateTv.text = date
        binding.communityfeedwriteDescriptionTv.text = description
    }
    private fun setupRecyclerView() {
        binding.communityfeedwriteRv.layoutManager = LinearLayoutManager(this)

        val items = listOf(
            CommunityFeedWriteItems(R.drawable.icon_profile,"닉네임", "안녕하세요 독서챌린지 참여하고 싶습니다.", "2024년 5월 19일"),
            CommunityFeedWriteItems(R.drawable.icon_profile,"너", "안녕하세요 ", "2024년 5월 9일"),
            CommunityFeedWriteItems(R.drawable.icon_profile,"나", "참여하고 싶습니다.", "2024년 5월"),
            CommunityFeedWriteItems(R.drawable.icon_profile,"우리", "안녕하세요 싶습니다.", "2024 19일"),
            CommunityFeedWriteItems(R.drawable.icon_profile,"우리", "안녕하세요 싶습니다.", "2024 19일"),
            CommunityFeedWriteItems(R.drawable.icon_profile,"우리", "안녕하세요 싶습니다.", "2024 19일"),
        )

        adapter = CommunityFeedWriteAdapter(items)
        binding.communityfeedwriteRv.adapter = adapter
    }
}
