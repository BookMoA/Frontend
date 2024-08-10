package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.study.GridBookDao
import com.bookmoa.android.databinding.ItemStorageBooksBinding

class StorageBookAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val listData: ArrayList<GridBookDao> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val binding : ItemStorageBooksBinding = ItemStorageBooksBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return GridItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GridItemViewHolder).onBind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    fun addItem(data: GridBookDao) {
        listData.add(data)
    }
    class GridItemViewHolder(val binding: ItemStorageBooksBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: GridBookDao) {

            binding.itemStorageCoverImgIv.setImageResource(data.img)
            binding.itemStorageTitleTv.text=data.title
            binding.itemStorageAuthorTv.text=data.author
        }
    }
}