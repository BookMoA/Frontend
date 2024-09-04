package com.bookmoa.android.group

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.adapter.CommunityFeedFragmentAdapter
import com.bookmoa.android.adapter.CommunityFeedWriteAdapter
import com.bookmoa.android.databinding.ActivityCommunityfeedwriteBinding
import com.bookmoa.android.services.Comment
import com.bookmoa.android.services.GetClubsPosts
import com.bookmoa.android.services.GetClubsPostsComments
import com.bookmoa.android.services.PostClubsPostsComments
import com.bookmoa.android.services.CreateCommentRequest
import com.bookmoa.android.services.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CommunityFeedWriteActivity : AppCompatActivity() {
    private val binding: ActivityCommunityfeedwriteBinding by lazy {
        ActivityCommunityfeedwriteBinding.inflate(layoutInflater)
    }
    private lateinit var adapter: CommunityFeedWriteAdapter
    private lateinit var feedAdapter: CommunityFeedFragmentAdapter
    private lateinit var tokenManager: TokenManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tokenManager = TokenManager(this)

        val postId = intent.getIntExtra("postId", -1)
        if (postId != -1) {
            fetchFeedData(postId)
        }

        binding.communityBackIv.setOnClickListener {
            finish()
        }

        val nickname = intent.getStringExtra("NICKNAME") ?: ""
        val description = intent.getStringExtra("DESCRIPTION") ?: ""
        val title = intent.getStringExtra("TITLE") ?: ""
        val date = intent.getStringExtra("DATE") ?: ""
        val clubName = intent.getStringExtra("clubName") ?: ""
        val clubIntro = intent.getStringExtra("clubIntro") ?: ""
        binding.communityfeedwriteNameTv.text = nickname
        binding.communityfeedwriteTitleTv.text = title
        binding.communityfeedwriteDateTv.text = date
        binding.communityfeedwriteDescriptionTv.text = description
        binding.communityTitleTv.text = clubName
        binding.communityDescriptionTv.text = clubIntro

        // Listen for the user pressing enter in the EditText
        binding.communityfeedwriteContentEt.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                val content = binding.communityfeedwriteContentEt.text.toString().trim()
                if (content.isNotEmpty()) {
                    postComment(postId, content)
                }
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun fetchFeedData(postId: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val clubApi = retrofit.create(GetClubsPosts::class.java)
        val commentsApi = retrofit.create(GetClubsPostsComments::class.java)
        val token = tokenManager.getToken()
        val bearerToken = "Bearer $token"

        if (token.isNullOrEmpty()) {
            Log.d("CommunityFeedFragment", "Token is null or empty")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                // Fetch post data
                val postResponse = clubApi.getClubsPosts(bearerToken, postId)
                if (postResponse.result) {
                    val postData = postResponse.data
                    binding.feedwriteCommentcountTv.text = postData.commentCount.toString()
                    binding.feedwriteLikecountTv.text = postData.likeCount.toString()
                } else {
                    Toast.makeText(this@CommunityFeedWriteActivity, "Failed to load post data", Toast.LENGTH_SHORT).show()
                }

                // Fetch comments data
                val commentsResponse = commentsApi.getComments(postId = postId.toLong(), page = 1)
                if (commentsResponse.result) {
                    val commentList = commentsResponse.data.commentList
                    setupRecyclerView(commentList)
                } else {
                    Toast.makeText(this@CommunityFeedWriteActivity, "Failed to load comments", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Log.e("CommunityFeedFragment", "Error fetching data", e)
            }
        }
    }

    private fun postComment(postId: Int, content: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val commentApi = retrofit.create(PostClubsPostsComments::class.java)
        val token = tokenManager.getToken()
        val bearerToken = "Bearer $token"

        if (token.isNullOrEmpty()) {
            Log.d("CommunityFeedFragment", "Token is null or empty")
            return
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val request = CreateCommentRequest(postId, content)
                val response = commentApi.createComment(bearerToken, request).execute()

                if (response.isSuccessful && response.body()?.result == true) {
                    Toast.makeText(this@CommunityFeedWriteActivity, "Comment posted successfully", Toast.LENGTH_SHORT).show()
                    // Clear the EditText
                    binding.communityfeedwriteContentEt.text.clear()
                    // Optionally, you could refresh the comment list here
                    fetchFeedData(postId)
                } else {
                    Toast.makeText(this@CommunityFeedWriteActivity, "Failed to post comment", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Log.e("CommunityFeedFragment", "Error posting comment", e)
                Toast.makeText(this@CommunityFeedWriteActivity, "An error occurred", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupRecyclerView(commentList: List<Comment>) {
        binding.communityfeedwriteRv.layoutManager = LinearLayoutManager(this)
        adapter = CommunityFeedWriteAdapter(commentList)
        binding.communityfeedwriteRv.adapter = adapter
    }
}
