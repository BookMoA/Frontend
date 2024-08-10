package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.databinding.FragmentGrouprvBinding

data class GroupRvItems(
    val title: String,
    val count: String,
    val description: String
)

class GroupRvFragmentAdapter(
    private val items: List<GroupRvItems>,
    private val onItemClick: (GroupRvItems) -> Unit): RecyclerView.Adapter<GroupRvFragmentAdapter.ViewHolder>() {
    class ViewHolder(private val binding: FragmentGrouprvBinding): RecyclerView.ViewHolder(binding.root) {
        fun bookclubRVbind(item: GroupRvItems, onItemClick: (GroupRvItems) -> Unit) {
            binding.grouprvTitleTv.text = item.title
            binding.grouprvCountTv.text = item.count
            binding.grouprvDescriptionTv.text = item.description
            itemView.setOnClickListener { onItemClick(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentGrouprvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bookclubRVbind(items[position], onItemClick)
    }

    override fun getItemCount() = items.size
}