package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.R
import com.bookmoa.android.databinding.ItemMemberBinding
import com.bookmoa.android.models.MemberInfoDTO
import com.bumptech.glide.Glide

class MemberInfoAdapter(
    private val members: List<MemberInfoDTO>
): RecyclerView.Adapter<MemberInfoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : ItemMemberBinding = ItemMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MemberInfoAdapter.ViewHolder, position: Int) {
        holder.bind(members[position])
    }

    override fun getItemCount(): Int {
        return members.size
    }

    inner class ViewHolder(val binding: ItemMemberBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(member: MemberInfoDTO) {
            binding.positionTv.text = member.adminRole
            binding.nicknameTv.text = member.nickName

            if (member.profileUrl != null) {
                Glide.with(itemView.context).load(member.profileUrl).into(binding.profileIv)
            } else {
                binding.profileIv.setImageResource(R.drawable.ic_profile_unfilled)
            }
        }
    }


}