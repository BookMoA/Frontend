package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.R
import com.bookmoa.android.databinding.ItemListTop10Binding
import com.bookmoa.android.models.SearchBookListData
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class SearchBookListAdapter(
    private val itemClickedListener: (SearchBookListData) -> Unit
) : ListAdapter<SearchBookListData, SearchBookListAdapter.ListItemViewHolder>(ListDiffCallback()) {

    inner class ListItemViewHolder(private val binding: ItemListTop10Binding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: SearchBookListData) {
            // 이미지 로딩
            Glide.with(binding.root.context)
                .load(data.img)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error)
                )
                .into(binding.itemListTop10ImgIv)

            // 데이터 바인딩
            binding.itemListTop10TitleTv.text = data.title
            binding.itemListTop10NumTv.text = "${data.bookCnt}개"
            binding.itemListTop10LikeTv.text = "${data.likeCnt}"

            // 클릭 리스너 설정
            binding.root.setOnClickListener {
                itemClickedListener(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val binding = ItemListTop10Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListDiffCallback : DiffUtil.ItemCallback<SearchBookListData>() {
        override fun areItemsTheSame(oldItem: SearchBookListData, newItem: SearchBookListData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: SearchBookListData, newItem: SearchBookListData): Boolean {
            return oldItem == newItem
        }
    }
}