package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.R
import com.bookmoa.android.databinding.ItemMemoListBinding
import com.bookmoa.android.models.SearchMemoData

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class SearchMemoAdapter(private val itemClickedListener: (SearchMemoData) -> Unit) : RecyclerView.Adapter<SearchMemoAdapter.ViewHolder>() {

    private val listData: ArrayList<SearchMemoData> = ArrayList()


    fun updateItems(newItems: List<SearchMemoData>) {
        listData.clear()
        listData.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding: ItemMemoListBinding = ItemMemoListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listData[position]
        holder.onBind(item)

    }

    override fun getItemCount(): Int = listData.size

    class ViewHolder(val binding: ItemMemoListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: SearchMemoData) {
            Glide.with(binding.root.context)
                .load(data.img)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.error)
                )
                .into(binding.itemSearchMemoCoverIv)

            binding.itemSearchMemoTitleTv.text = data.title
            binding.itemMemoAuthorTv.text = "${data.writer}개"
            binding.itemMemoContentsTv.text = data.memo
        }
    }
}