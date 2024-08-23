package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.databinding.FragmentNextleaderrvBinding

data class NextLeaderItems(
    val isLeader: Boolean,
    val profile: Int,
    val name: String,
    val isDetail: Boolean
)

class NextLeaderFragmentAdapter(private val nextLeaderItems: List<NextLeaderItems>) : RecyclerView.Adapter<NextLeaderFragmentAdapter.ViewHolder>() {
    class ViewHolder(private val binding: FragmentNextleaderrvBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NextLeaderItems) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentNextleaderrvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(nextLeaderItems[position])
    }

    override fun getItemCount() = nextLeaderItems.size
}