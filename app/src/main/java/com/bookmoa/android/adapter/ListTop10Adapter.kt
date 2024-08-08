package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.study.ListTop10Dao
import com.bookmoa.android.databinding.ItemListTop10Binding

class ListTop10Adapter: RecyclerView.Adapter<ListTop10Adapter.ViewHolder>() {

    private val listData: ArrayList<ListTop10Dao> = ArrayList()

    interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
    }
    fun setOnItemclickListner(onItemClickListner: OnItemClickListener){
        itemClickListner = onItemClickListner
    }

    private lateinit var itemClickListner: OnItemClickListener

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemListTop10Binding = ItemListTop10Binding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    fun addItem(data: ListTop10Dao) {
        listData.add(data)
    }
    class ViewHolder(val binding: ItemListTop10Binding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: ListTop10Dao){
            binding.itemListTop10ImgIv.setImageResource(data.img)
            binding.itemListTop10TitleTv.text=data.title
            binding.itemListTop10NumTv.text="${data.num}개"
            binding.itemListTop10LikeTv.text="${data.like}"
        }

    }
}