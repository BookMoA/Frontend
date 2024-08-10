package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.study.StorageListDao
import com.bookmoa.android.databinding.ItemStorageListBinding

class StorageListAdapter: RecyclerView.Adapter<StorageListAdapter.ViewHolder>() {

    private val listData: ArrayList<StorageListDao> = ArrayList()
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemStorageListBinding = ItemStorageListBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBind(listData[position])
        holder
    }

    override fun getItemCount(): Int = listData.size

    fun addItem(data: StorageListDao) {
        listData.add(data)
    }
    class ViewHolder(val binding: ItemStorageListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: StorageListDao){
            binding.myStorageListImgIv.setImageResource(data.img)
            binding.itemBookListTitleTv.text=data.title
            binding.itemBookListNumTv.text="${data.num}개"
        }


    }
}