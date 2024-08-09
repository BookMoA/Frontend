package com.bookmoa.and.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.and.databinding.FragmentInsearcharticlervBinding

data class InSearchArticleItems(
    val title: String,
    val description: String,
    val date: String,
    val image: Int
)

class InSearchArticleFragmentAdapter(private val articleItems: List<InSearchArticleItems>) : RecyclerView.Adapter<InSearchArticleFragmentAdapter.ViewHolder>() {
    class ViewHolder(private val binding: FragmentInsearcharticlervBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: InSearchArticleItems) {
            binding.insearcharticleTitleTv.text = item.title
            binding.insearcharticleDescriptionTv.text = item.description
            binding.insearcharticleDateTv.text = item.date
            binding.insearcharticleImageIv.setImageResource(item.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentInsearcharticlervBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(articleItems[position])
    }

    override fun getItemCount() = articleItems.size
}