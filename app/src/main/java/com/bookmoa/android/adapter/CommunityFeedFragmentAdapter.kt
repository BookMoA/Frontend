package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.R
import com.bookmoa.android.databinding.FragmentCommunityfeedrvBinding

data class CommunityFeedItems(
    val postId: Int,
    val profile: Int,
    val name: String,
    val date: String,
    val title: String,
    val description: String
)

class CommunityFeedFragmentAdapter(
    private var feedItems: List<CommunityFeedItems>,
    private val listener: OnItemClickListener,
    private val postLike: (Int) -> Unit,
    private val deletePostLike: (Int) -> Unit

) : RecyclerView.Adapter<CommunityFeedFragmentAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onCommentClick(item: CommunityFeedItems)
    }

    class ViewHolder(private val binding: FragmentCommunityfeedrvBinding) : RecyclerView.ViewHolder(binding.root) {
        private var isLiked: Boolean = false

        fun bind(item: CommunityFeedItems, listener: OnItemClickListener, postLike: (Int) -> Unit, deletePostLike: (Int) -> Unit) {
            binding.communityfeedProfileIv.setImageResource(item.profile)
            binding.communityfeedNameTv.text = item.name
            binding.communityfeedDateTv.text = item.date
            binding.communityfeedTitleTv.text = item.title
            binding.communityfeedDescriptionTv.text = item.description

            binding.communityfeedCommentBtn.setOnClickListener {
                listener.onCommentClick(item)
            }
            binding.communityfeedLikeBtn.setOnClickListener {
                toggleLike(item, postLike, deletePostLike)
            }
        }


        private fun toggleLike(item: CommunityFeedItems, postLike: (Int) -> Unit, deletePostLike: (Int) -> Unit) {
            if (isLiked) {
                binding.communityfeedLikeIv.setImageResource(R.drawable.icon_like)
                deletePostLike(item.postId) // Use the deletePostLike function passed as a parameter
            } else {
                binding.communityfeedLikeIv.setImageResource(R.drawable.icon_liked)
                postLike(item.postId) // Pass the post ID to the like API call
            }
            isLiked = !isLiked
        }

    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentCommunityfeedrvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feedItems[position], listener, postLike, deletePostLike)
    }

    override fun getItemCount() = feedItems.size

    fun updateItems(newItems: List<CommunityFeedItems>) {
        feedItems = newItems
        notifyDataSetChanged()
    }
}