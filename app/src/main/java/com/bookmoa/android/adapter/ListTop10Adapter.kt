package com.bookmoa.android.study

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.R
import com.bookmoa.android.databinding.ItemListTop10Binding
import com.bookmoa.android.models.ListTop10Data
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class ListTop10Adapter : RecyclerView.Adapter<ListTop10Adapter.ViewHolder>() {

    private val listData: ArrayList<ListTop10Data> = ArrayList()

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, data: ListTop10Data)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    fun updateItems(newItems: List<ListTop10Data>) {
        listData.clear()
        listData.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemListTop10Binding = ItemListTop10Binding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listData[position]
        holder.onBind(item)

        holder.itemView.setOnClickListener {
            itemClickListener?.onItemClick(it, position, item)
        }
    }

    override fun getItemCount(): Int = listData.size

    class ViewHolder(val binding: ItemListTop10Binding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: ListTop10Data) {
            Glide.with(binding.root.context)
                .load(data.img)
                .centerCrop()
                .apply(RequestOptions()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                )
                .into(binding.itemListTop10ImgIv)

            binding.itemListTop10TitleTv.text = data.title
            binding.itemListTop10NumTv.text = "${data.bookCnt}개"
            binding.itemListTop10LikeTv.text = "${data.likeCnt}"
        }
    }
}