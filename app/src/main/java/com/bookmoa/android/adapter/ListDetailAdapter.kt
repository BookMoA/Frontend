package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.study.ItemListDetailDao
import com.bookmoa.android.databinding.ItemBookListBinding

class ListDetailAdapter : RecyclerView.Adapter<ListDetailAdapter.ViewHolder>() {

    private val listData: ArrayList<ItemListDetailDao> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemBookListBinding = ItemBookListBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    fun addItem(data: ItemListDetailDao) {
        listData.add(data)
    }
    class ViewHolder(val binding: ItemBookListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: ItemListDetailDao){
            binding.itemBookListCoverIv.setImageResource(data.img)
            binding.itemBookListTitleTv.text=data.title
            binding.itemBookListAuthorTv.text=data.author
        }

    }
}