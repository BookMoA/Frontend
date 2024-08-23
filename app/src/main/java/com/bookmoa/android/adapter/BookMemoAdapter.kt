package com.bookmoa.android.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.databinding.ItemBookMemoBinding
import com.bookmoa.android.memo.BookMemoFragment
import com.bookmoa.android.models.BookMemoDTO
import com.bumptech.glide.Glide

class BookMemoAdapter(
    private val bookList: MutableList<BookMemoDTO>,
    private var isDeleteMode: Boolean,
    private val selectedItems: MutableList<BookMemoDTO>,
    private val callback: OnItemSelectedListener
) : RecyclerView.Adapter<BookMemoAdapter.ViewHolder> () {

    interface OnItemSelectedListener {
        fun onItemSelectedChanged()  // 선택 상태 변경 시 호출될 메서드
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookMemoAdapter.ViewHolder {
        val binding: ItemBookMemoBinding =
            ItemBookMemoBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        Log.d("[Memo]", "Memo: $bookList")
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookMemoAdapter.ViewHolder, position: Int) {
        holder.bind(bookList[position])
    }

    inner class ViewHolder(val binding: ItemBookMemoBinding) :
        RecyclerView.ViewHolder(binding.root) {

            fun bind(item: BookMemoDTO) {
                binding.bookNameTv.text = item.title
                binding.bookAuthorTv.text = item.writer
                Glide.with(itemView.context).load(item.image).into(binding.bookIv)

                if (isDeleteMode && selectedItems.contains(item)) {
                    Log.d("[MEMO/DELETE]", "어댑터 - 삭제 UI")
                    binding.overlayIv.visibility = View.VISIBLE
                } else {
                    Log.d("[MEMO/DELETE]", "어댑터 - 기존 UI")
                    binding.overlayIv.visibility = View.GONE
                }
                // (itemView.context as? BookMemoFragment)?.updateButtonState()
            }

            init {
                itemView.setOnClickListener {
                    if (isDeleteMode) {
                        val book = bookList[adapterPosition]
                        if (selectedItems.contains(book)) {
                            selectedItems.remove(book)
                            // 선택 해제 UI 처리
                            Log.d("[MEMO/DELETE]", "어댑터 - 선택 해제")
                            binding.overlayIv.visibility = View.GONE
                        } else {
                            selectedItems.add(book)
                            // 선택된 UI 처리
                            Log.d("[MEMO/DELETE]", "어댑터 - 선택")
                            binding.overlayIv.visibility = View.VISIBLE
                        }
                        callback.onItemSelectedChanged()
                    }
                }
            }
        }

    fun updateData(newData: MutableList<BookMemoDTO>) {
        bookList.clear()
        bookList.addAll(newData)
        notifyDataSetChanged()
    }

    fun setDeleteMode(isDeleteMode: Boolean) {
        this.isDeleteMode = isDeleteMode
        notifyDataSetChanged() // 삭제 모드로 전환 시 전체 갱신
    }

    fun removeItem(item: BookMemoDTO) {
        val position = bookList.indexOf(item)
        if (position != -1) {
            bookList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRemoved(position)
        }
    }
}