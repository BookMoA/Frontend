package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.databinding.FragmentNextleaderBinding
import com.bookmoa.android.databinding.FragmentNextleaderrvBinding
import com.bookmoa.android.R // Import R to access drawable resources

data class NextLeaderItems(
    val isLeader: Boolean,
    val profile: Int,
    val name: String,
    val isDetail: Boolean
)

class NextLeaderFragmentAdapter(private var nextLeaderItems: List<NextLeaderItems>, private val callback: OnSelectChangeListener) : RecyclerView.Adapter<NextLeaderFragmentAdapter.ViewHolder>() {

    interface OnSelectChangeListener {
        fun onSelectionChange(isSelected: Boolean)
    }

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    class ViewHolder(private val binding: FragmentNextleaderrvBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NextLeaderItems, isSelected: Boolean, callback: OnSelectChangeListener) {
            // Bind data to the views
            binding.nextleaderProfileIv.setImageResource(item.profile)
            binding.nextleaderNameTv.text = item.name

            // Show crown if the member is the leader, otherwise hide it
            if (item.isLeader) {
                binding.nextleaderCrownIv.visibility = View.VISIBLE
            } else {
                binding.nextleaderCrownIv.visibility = View.GONE
            }

            // Show description if isDetail is true, otherwise hide it
            if (item.isDetail) {
                binding.nextleaderDescriptionTv.visibility = View.VISIBLE
            } else {
                binding.nextleaderDescriptionTv.visibility = View.GONE
            }

            // Show or hide nextleader_select_iv and nextleader_grey_iv based on isSelected and isLeader
            if (!item.isLeader && isSelected) {
                binding.nextleaderSelectIv.visibility = View.VISIBLE
                binding.nextleaderGreyIv.visibility = View.VISIBLE
                callback.onSelectionChange(true) // Trigger the callback when both are visible
            } else {
                binding.nextleaderSelectIv.visibility = View.GONE
                binding.nextleaderGreyIv.visibility = View.GONE
                callback.onSelectionChange(false) // Trigger the callback when they are not visible
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentNextleaderrvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = nextLeaderItems[position]
        holder.bind(item, position == selectedPosition, callback)

        holder.itemView.setOnClickListener {
            if (!item.isLeader) {
                val previousSelectedPosition = selectedPosition
                selectedPosition = holder.adapterPosition

                // Notify the adapter that the previous and the current selected positions have changed
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)
            }
        }
    }

    override fun getItemCount() = nextLeaderItems.size

    fun updateItems(newItems: List<NextLeaderItems>) {
        nextLeaderItems = newItems
        notifyDataSetChanged()
    }
}

