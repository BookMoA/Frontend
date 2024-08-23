package com.bookmoa.android.group

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.R
import com.bookmoa.android.TokenManager
import com.bookmoa.android.adapter.CommunityFeedFragmentAdapter
import com.bookmoa.android.adapter.CommunityFeedItems
import com.bookmoa.android.databinding.FragmentCommunityfeedvpBinding
import com.bookmoa.android.interfaces.CreatePostLikeRequest
import com.bookmoa.android.interfaces.GetClubsMembers
import com.bookmoa.android.interfaces.GetClubsPostsList
import com.bookmoa.android.interfaces.PostClubsPostsLikes
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CommunityFeedFragment : Fragment(), CommunityFeedFragmentAdapter.OnItemClickListener {

    private var _binding: FragmentCommunityfeedvpBinding? = null
    private val binding get() = _binding!!
    private lateinit var tokenManager: TokenManager
    private lateinit var feedAdapter: CommunityFeedFragmentAdapter
    private var clubId: Int? = null
    private var clubName: String? = null
    private var clubIntro: String? = null
    private var membersMap: Map<Int, String> = emptyMap() // Map to hold memberId to nickname

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            clubId = it.getInt("clubId")
            clubName = it.getString("clubName")
            clubIntro = it.getString("clubIntro")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCommunityfeedvpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tokenManager = TokenManager(requireContext())

        Log.d("CommunityFeedFragment", "Club Name: $clubName, Club Intro: $clubIntro")

        binding.communityfeedRv.layoutManager = LinearLayoutManager(context)
        feedAdapter = CommunityFeedFragmentAdapter(emptyList(), this) { postId ->
            postLike(postId)
        }
        binding.communityfeedRv.adapter = feedAdapter

        fetchMembersData() // Fetch members data first
    }

    private fun fetchMembersData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val clubApi = retrofit.create(GetClubsMembers::class.java)
        val token = tokenManager.getToken()
        val bearerToken = "Bearer $token"
        if (token.isNullOrEmpty()) {
            Log.d("CommunityFeedFragment", "Token is null or empty")
            return
        }

        lifecycleScope.launch {
            try {
                val response = clubApi.getClubsMembers(bearerToken, clubId = clubId ?: 0)
                if (response.result) {
                    // Create a map of memberId to nickname
                    membersMap = response.data.associate { it.memberId to it.nickname }
                    fetchFeedData() // Now fetch the feed data after members data is available
                } else {
                    Log.d("CommunityFeedFragment", "API call failed: ${response.description}")
                }
            } catch (e: Exception) {
                Log.e("CommunityFeedFragment", "Error fetching members data", e)
            }
        }
    }
    private fun postLike(postId: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val likeApi = retrofit.create(PostClubsPostsLikes::class.java)
        val token = tokenManager.getToken()
        val bearerToken = "Bearer $token"
        if (token.isNullOrEmpty()) {
            Log.d("CommunityFeedFragment", "Token is null or empty")
            return
        }

        val request = CreatePostLikeRequest(postId)

        lifecycleScope.launch {
            try {
                val response = likeApi.createPostLike(bearerToken, request).execute()
                if (response.isSuccessful && response.body()?.result == true) {
                    Log.d("CommunityFeedFragment", "Post liked successfully")
                    // Handle the successful like response if needed
                } else {
                    Log.d("CommunityFeedFragment", "Failed to like post: ${response.body()?.description}")
                }
            } catch (e: Exception) {
                Log.e("CommunityFeedFragment", "Error liking post", e)
            }
        }
    }

    private fun fetchFeedData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val clubApi = retrofit.create(GetClubsPostsList::class.java)
        val token = tokenManager.getToken()
        val bearerToken = "Bearer $token"
        if (token.isNullOrEmpty()) {
            Log.d("CommunityFeedFragment", "Token is null or empty")
            return
        }

        lifecycleScope.launch {
            try {
                val response = clubApi.getClubsPostsList(bearerToken, clubId = clubId ?: 0)
                if (response.result) {
                    val feedItems = response.data.postList.map { post ->
                        val formattedDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            LocalDate.parse(post.createAt.substring(0, 10)).format(
                                DateTimeFormatter.ISO_DATE)
                        } else {
                            TODO("VERSION.SDK_INT < O")
                        }
                        // Get nickname from membersMap, default to "User {memberId}" if not found
                        val nickname = membersMap[post.memberId] ?: "User ${post.memberId}"
                        CommunityFeedItems(
                            postId = post.postId,
                            profile = R.drawable.icon_profile,
                            name = nickname,
                            date = formattedDate,
                            title = post.title,
                            description = post.context
                        )
                    }
                    feedAdapter.updateItems(feedItems)
                } else {
                    Log.d("CommunityFeedFragment", "API call failed: ${response.description}")
                }
            } catch (e: Exception) {
                Log.e("CommunityFeedFragment", "Error fetching feed data", e)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCommentClick(item: CommunityFeedItems) {
        val intent = Intent(requireContext(), CommunityFeedWriteActivity::class.java).apply {
            putExtra("NICKNAME", item.name)
            putExtra("DESCRIPTION", item.description)
            putExtra("TITLE", item.title)
            putExtra("DATE", item.date)
        }
        startActivity(intent)
    }
}
