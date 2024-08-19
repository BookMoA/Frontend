package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.R
import com.bookmoa.android.study.ItemListDetailDao
import com.bookmoa.android.databinding.ItemBookListBinding
import com.bookmoa.android.study.ListContentBook
import com.bumptech.glide.Glide

class ListDetailAdapter : RecyclerView.Adapter<ListDetailAdapter.ViewHolder>() {

    private val listData: ArrayList<ListContentBook> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemBookListBinding = ItemBookListBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    fun updateItems(newItems: List<ListContentBook>) {
        listData.clear()
        listData.addAll(newItems)
        notifyDataSetChanged()
    }

    // ViewHolder 정의
    class ViewHolder(private val binding: ItemBookListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: ListContentBook) {
            // 이미지 설정 (이미지가 URL인 경우 Glide나 Picasso 사용)
            Glide.with(binding.root.context)
                .load(book.coverImg)  // 이미지 URL
                .placeholder(R.drawable.placeholder) // 로딩 중에 표시할 이미지
                .error(R.drawable.error) // 로딩 실패 시 표시할 이미지
                .into(binding.itemBookListCoverIv)
            binding.itemBookListTitleTv.text = book.title
            binding.itemBookListAuthorTv.text = book.writer
        }
    }
}