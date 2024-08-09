package com.bookmoa.and.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.and.databinding.FragmentInsearchtitlervBinding

data class InSearchTitleItems(
    val title: String,
    val description: String,
    val date: String,
    val image: Int
)

class InSearchTitleFragmentAdapter(private val titleItems: List<InSearchTitleItems>) : RecyclerView.Adapter<InSearchTitleFragmentAdapter.ViewHolder>() {
    class ViewHolder(private val binding: FragmentInsearchtitlervBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: InSearchTitleItems){
            binding.insearchtitleTitleTv.text = item.title
            binding.insearchtitleDescriptionTv.text = item.description
            binding.insearchtitleDateTv.text = item.date
            binding.insearchtitleImageIv.setImageResource(item.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentInsearchtitlervBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(titleItems[position])
    }

    override fun getItemCount() = titleItems.size
}