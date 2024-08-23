package com.bookmoa.android.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.R
import com.bookmoa.android.databinding.ItemStorageListBinding
import com.bookmoa.android.models.StorageListData
import com.bumptech.glide.Glide

class StorageListEditAdapter(private val selectedIds: MutableSet<Int>) : RecyclerView.Adapter<StorageListEditAdapter.ViewHolder>() {

    private val listData: ArrayList<StorageListData> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemStorageListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    fun updateItems(newItems: List<StorageListData>) {
        listData.clear()
        listData.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemStorageListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: StorageListData) {
            Glide.with(binding.root.context)
                .load(data.img)  // 이미지 URL
                .centerCrop()
                .placeholder(R.drawable.placeholder) // 로딩 중에 표시할 이미지
                .error(R.drawable.error) // 로딩 실패 시 표시할 이미지
                .into(binding.itemBookListImgIv)

            binding.itemBookListTitleTv.text=data.title
            binding.itemBookListNumTv.text="${data.bookCnt}개"

            if(data.listStatus=="PUBLIC"){
                binding.myStorageListUnlockIcon.visibility= View.VISIBLE
                binding.myStorageListLockIcon.visibility= View.GONE
            }
            else{
                binding.myStorageListUnlockIcon.visibility= View.GONE
                binding.myStorageListLockIcon.visibility= View.VISIBLE
            }

            itemView.setOnClickListener {
                if (selectedIds.contains(data.bookListId)) {
                    selectedIds.remove(data.bookListId)
                    itemView.setBackgroundColor(Color.WHITE)
                } else {
                    selectedIds.add(data.bookListId)
                    itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.main30))
                }
            }

            // 선택 상태에 따라 배경색 설정
            if (selectedIds.contains(data.bookListId)) {
                itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.main30))
            } else {
                itemView.setBackgroundColor(Color.WHITE)
            }
        }
    }
}