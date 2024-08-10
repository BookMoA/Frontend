package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.databinding.FragmentCommunityfeedrvBinding

data class CommunityFeedItems(
    val profile: Int,
    val name: String,
    val date: String,
    val title: String,
    val description: String)

class CommunityFeedFragmentAdapter(private val feeditems: List<CommunityFeedItems>): RecyclerView.Adapter<CommunityFeedFragmentAdapter.ViewHolder>() {
    class ViewHolder(private val binding: FragmentCommunityfeedrvBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CommunityFeedItems){
            binding.communityfeedProfileIv.setImageResource(item.profile)
            binding.communityfeedNameTv.text = item.name
            binding.communityfeedDateTv.text = item.date
            binding.communityfeedTitleTv.text = item.title
            binding.communityfeedDescriptionTv.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentCommunityfeedrvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(feeditems[position])
    }

    override fun getItemCount() = feeditems.size

}