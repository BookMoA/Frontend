package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.databinding.FragmentGrouprvBinding

data class GroupRvItems(
    val clubId: Int,
    val name: String,
    val intro: String,
    val createAt: String,
    val updateAt: String,
    val memberCount: Int,
    val postCount: Int
)

class GroupRvFragmentAdapter(private val items: List<GroupRvItems>, private val onItemClick: (Int) -> Unit): RecyclerView.Adapter<GroupRvFragmentAdapter.ViewHolder>() {
    class ViewHolder(private val binding: FragmentGrouprvBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GroupRvItems, onItemClick: (Int) -> Unit) {
            binding.grouprvTitleTv.text = item.name
            binding.grouprvDescriptionTv.text = item.intro
            binding.grouprvCountTv.text = "${item.memberCount}/20"
            binding.root.setOnClickListener {
                onItemClick(item.clubId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentGrouprvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, onItemClick)
    }

    override fun getItemCount() = items.size


}