package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.R
import com.bookmoa.android.databinding.ItemStorageListBinding
import com.bookmoa.android.models.StorageListData
import com.bumptech.glide.Glide

class StorageListAdapter: RecyclerView.Adapter<StorageListAdapter.ViewHolder>() {

    private val listData: ArrayList<StorageListData> = ArrayList()

    interface OnItemClickListener {
        fun onItemClick(view: View, position: Int, data: StorageListData)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.itemClickListener = listener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemStorageListBinding = ItemStorageListBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)

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

    fun updateItems(newItems: List<StorageListData>) {
        listData.clear()
        listData.addAll(newItems)
        notifyDataSetChanged()
    }
    class ViewHolder(val binding: ItemStorageListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun onBind(data: StorageListData){
            Glide.with(binding.root.context)
                .load(data.img)  // 이미지 URL
                .centerCrop()
                .placeholder(R.drawable.placeholder) // 로딩 중에 표시할 이미지
                .error(R.drawable.error) // 로딩 실패 시 표시할 이미지
                .into(binding.itemBookListImgIv)

            binding.itemBookListTitleTv.text=data.title
            binding.itemBookListNumTv.text="${data.bookCnt}개"

            if(data.listStatus=="PUBLIC"){
                binding.myStorageListUnlockIcon.visibility=View.VISIBLE
                binding.myStorageListLockIcon.visibility=View.GONE
            }
            else{
                binding.myStorageListUnlockIcon.visibility=View.GONE
                binding.myStorageListLockIcon.visibility=View.VISIBLE
            }
        }


    }
}