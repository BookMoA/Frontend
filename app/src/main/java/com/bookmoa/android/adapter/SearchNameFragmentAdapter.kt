package com.bookmoa.android.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.R
import com.bookmoa.android.databinding.FragmentSearchnamervBinding

data class SearchNameItems(
    val title: String,
    val count: String,
    val description: String
)

class SearchNameFragmentAdapter(private var nameitems: List<SearchNameItems>, private var searchQuery: String = "") : RecyclerView.Adapter<SearchNameFragmentAdapter.ViewHolder>() {

    class ViewHolder(private val binding: FragmentSearchnamervBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SearchNameItems, searchQuery: String) {
            binding.searchnameTitleTv.text = highlightText(item.title, searchQuery)
            binding.searchnameCountTv.text = item.count
            binding.searchnameDescriptionTv.text = highlightText(item.description, searchQuery)
        }

        private fun highlightText(text: String, searchQuery: String): SpannableString {
            val spannableString = SpannableString(text)
            if (searchQuery.isNotEmpty()) {
                val color = ContextCompat.getColor(itemView.context, R.color.main_color)
                val startIndex = text.indexOf(searchQuery, ignoreCase = true)
                if (startIndex != -1) {
                    spannableString.setSpan(
                        ForegroundColorSpan(color),
                        startIndex,
                        startIndex + searchQuery.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
            return spannableString
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentSearchnamervBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(nameitems[position], searchQuery)
    }

    override fun getItemCount() = nameitems.size

    fun updateData(newItems: List<SearchNameItems>, newSearchQuery: String) {
        nameitems = newItems
        searchQuery = newSearchQuery
        notifyDataSetChanged()
    }
}