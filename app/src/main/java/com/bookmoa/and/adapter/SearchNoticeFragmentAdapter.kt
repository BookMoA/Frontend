package com.bookmoa.and.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.and.databinding.FragmentSearchnoticervBinding
import com.bookmoa.and.group.SearchNoticeFragment

data class SearchNoticeItems(
    val title: String,
    val count: String,
    val description: String
)

class SearchNoticeFragmentAdapter(private val noticeitems: List<SearchNoticeItems>) :
    RecyclerView.Adapter<SearchNoticeFragmentAdapter.ViewHolder>() {
    class ViewHolder(private val binding: FragmentSearchnoticervBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchNoticeItems) {
            binding.searchnoticeTitleTv.text = item.title
            binding.searchnoticeCountTv.text = item.count
            binding.searchnoticeDescriptionTv.text = item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentSearchnoticervBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(noticeitems[position])
    }

    override fun getItemCount() = noticeitems.size
}