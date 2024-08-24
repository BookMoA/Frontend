package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.R
import com.bookmoa.android.databinding.FragmentCommunityfeedwritervBinding
import com.bookmoa.android.services.Comment

class CommunityFeedWriteAdapter(private val commentList: List<Comment>) : RecyclerView.Adapter<CommunityFeedWriteAdapter.ViewHolder>() {

    class ViewHolder(private val binding: FragmentCommunityfeedwritervBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: Comment) {
            // Assuming you have a placeholder image for profiles
            binding.communityfeedwriteProfileIv.setImageResource(R.drawable.icon_profile)
            binding.communityfeedwriteNameTv.text = comment.nickname
            binding.communityfeedwriteContentTv.text = comment.context
            binding.communityfeedwriteDateTv.text = comment.createAt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentCommunityfeedwritervBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(commentList[position])
    }

    override fun getItemCount() = commentList.size
}
