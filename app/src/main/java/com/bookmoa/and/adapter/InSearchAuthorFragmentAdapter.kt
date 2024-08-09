package com.bookmoa.and.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.and.databinding.FragmentInsearchauthorrvBinding

data class InSearchAuthorItems(
    val title: String,
    val description: String,
    val date: String,
    val image: Int
)

class InSearchAuthorFragmentAdapter(private val authorItems: List<InSearchAuthorItems>) : RecyclerView.Adapter<InSearchAuthorFragmentAdapter.ViewHolder>() {
    class ViewHolder(private val binding: FragmentInsearchauthorrvBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bind(item: InSearchAuthorItems){
            binding.insearchauthorTitleTv.text = item.title
            binding.insearchauthorDescriptionTv.text = item.description
            binding.insearchauthorDateTv.text = item.date
            binding.insearchauthorImageIv.setImageResource(item.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentInsearchauthorrvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(authorItems[position])
    }

    override fun getItemCount() = authorItems.size
}