package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.R
import com.bookmoa.android.databinding.ItemStorageBooksBinding
import com.bookmoa.android.models.StorageBookData
import com.bumptech.glide.Glide

class StorageBookAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listData: ArrayList<StorageBookData> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding : ItemStorageBooksBinding = ItemStorageBooksBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GridItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GridItemViewHolder).onBind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    fun updateItems(newItems: List<StorageBookData>) {
        listData.clear()
        listData.addAll(newItems)
        notifyDataSetChanged()
    }
    class GridItemViewHolder(val binding: ItemStorageBooksBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: StorageBookData) {

            Glide.with(binding.root.context)
                .load(data.coverImage)  // 이미지 URL
                .centerCrop()
                .placeholder(R.drawable.placeholder) // 로딩 중에 표시할 이미지
                .error(R.drawable.error) // 로딩 실패 시 표시할 이미지
                .into(binding.itemStorageCoverImgIv)

            binding.itemStorageTitleTv.text=data.title
            binding.itemStorageAuthorTv.text=data.writer
        }
    }
}