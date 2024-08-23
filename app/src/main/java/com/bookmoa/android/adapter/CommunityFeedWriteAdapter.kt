package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.databinding.FragmentCommunityfeedwritervBinding

data class CommunityFeedWriteItems(
    val profile: Int,
    val name: String,
    val content: String,
    val date: String
)

class CommunityFeedWriteAdapter(private val writeItems: List<CommunityFeedWriteItems>) : RecyclerView.Adapter<CommunityFeedWriteAdapter.ViewHolder>() {
    class ViewHolder(private val binding: FragmentCommunityfeedwritervBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommunityFeedWriteItems) {
            binding.communityfeedwriteProfileIv.setImageResource(item.profile)
            binding.communityfeedwriteNameTv.text = item.name
            binding.communityfeedwriteContentTv.text = item.content
            binding.communityfeedwriteDateTv.text = item.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentCommunityfeedwritervBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(writeItems[position])
    }

    override fun getItemCount() = writeItems.size
}