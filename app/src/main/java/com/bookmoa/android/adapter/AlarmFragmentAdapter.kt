package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.databinding.FragmentAlarmrvBinding

data class AlarmItems(
    val isMegaphone: Boolean,
    val title: String,
    val date: String,
    val content: String,
    val isExpand: Boolean
)

class AlarmFragmentAdapter(private val alarmItems: List<AlarmItems>) : RecyclerView.Adapter<AlarmFragmentAdapter.ViewHolder>() {
    class ViewHolder(private val binding: FragmentAlarmrvBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: AlarmItems) {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentAlarmrvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(alarmItems[position])
    }

    override fun getItemCount() = alarmItems.size
}