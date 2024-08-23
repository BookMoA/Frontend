
// BookAdapter.kt
package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.databinding.ItemBookListBinding
import com.bookmoa.android.models.SearchBookData
import com.bumptech.glide.Glide
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.content.Context

class SearchBookAdapter(
    private val itemClickedListener: (SearchBookData) -> Unit,
    private val query: String // Query for highlighting
) : ListAdapter<SearchBookData, SearchBookAdapter.BookItemViewHolder>(BookDiffCallback()) {

    inner class BookItemViewHolder(private val binding: ItemBookListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bookModel: SearchBookData) {
            binding.itemBookListTitleTv.text = highlightText(bookModel.title, query)
            binding.itemBookListAuthorTv.text = bookModel.author

            // Load image
            Glide.with(binding.itemBookListCoverIv.context)
                .load(bookModel.coverSmallUrl)
                .into(binding.itemBookListCoverIv)

            // Set click listener
            binding.root.setOnClickListener {
                itemClickedListener(bookModel)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder {
        val binding = ItemBookListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // DiffUtil callback
    class BookDiffCallback : DiffUtil.ItemCallback<SearchBookData>() {
        override fun areItemsTheSame(oldItem: SearchBookData, newItem: SearchBookData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchBookData, newItem: SearchBookData): Boolean {
            return oldItem == newItem
        }
    }

    // Highlight matching text in the title
    private fun highlightText(text: String, query: String): SpannableString {
        val spannableString = SpannableString(text)
        if (query.isEmpty()) return spannableString

        val lowerCaseText = text.lowercase()
        val lowerCaseQuery = query.lowercase()
        val startIndex = lowerCaseText.indexOf(lowerCaseQuery)

        if (startIndex != -1) {
            val endIndex = startIndex + query.length
            val colorSpan = ForegroundColorSpan(0xFFFF0000.toInt()) // Red color
            spannableString.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return spannableString
    }
}