import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.R
import com.bookmoa.android.databinding.ItemBookListBinding
import com.bookmoa.android.models.ListContentBook
import com.bumptech.glide.Glide

class ListContentAdapter(private val selectedIds: MutableSet<Int>) : RecyclerView.Adapter<ListContentAdapter.ViewHolder>() {

    private val listData: ArrayList<ListContentBook> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int = listData.size

    fun updateItems(newItems: List<ListContentBook>) {
        listData.clear()
        listData.addAll(newItems)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemBookListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(book: ListContentBook) {
            Glide.with(binding.root.context)
                .load(book.coverImg)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .into(binding.itemBookListCoverIv)
            binding.itemBookListTitleTv.text = book.title
            binding.itemBookListAuthorTv.text = book.writer

            itemView.setOnClickListener {
                if (selectedIds.contains(book.bookId)) {
                    selectedIds.remove(book.bookId)
                    itemView.setBackgroundColor(Color.WHITE)
                } else {
                    selectedIds.add(book.bookId)
                    itemView.setBackgroundColor(Color.LTGRAY)
                }
            }

            // 선택 상태에 따라 배경색 설정
            if (selectedIds.contains(book.bookId)) {
                itemView.setBackgroundColor(Color.LTGRAY)
            } else {
                itemView.setBackgroundColor(Color.WHITE)
            }
        }
    }
}