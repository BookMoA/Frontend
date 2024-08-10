package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.databinding.FragmentSearchnamervBinding

data class SearchNameItems(
    val title: String,
    val count: String,
    val description: String
)

class SearchNameFragmentAdapter(private val nameitems: List<SearchNameItems>) : RecyclerView.Adapter<SearchNameFragmentAdapter.ViewHolder>(){
    class ViewHolder(private val binding: FragmentSearchnamervBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchNameItems) {
            binding.searchnameTitleTv.text = item.title
            binding.searchnameCountTv.text = item.count
            binding.searchnameDescriptionTv.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentSearchnamervBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(nameitems[position])
    }

    override fun getItemCount() = nameitems.size
}