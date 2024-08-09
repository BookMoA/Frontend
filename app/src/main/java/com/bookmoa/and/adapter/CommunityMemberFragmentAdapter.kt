package com.bookmoa.and.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.and.R
import com.bookmoa.and.databinding.FragmentCommunitymemberrvBinding

data class CommunityMemberItems(
    val name: String,
    val floatmsg: String,
    val isLeader: Boolean,
    val float: Boolean
)

class CommunityMemberFragmentAdapter(
    private val members: MutableList<CommunityMemberItems>,
    private val onItemClick: (CommunityMemberItems) -> Unit,
    private val onCloseClick: (CommunityMemberItems) -> Unit
) : RecyclerView.Adapter<CommunityMemberFragmentAdapter.BookClubCommunityMemberViewHolder>() {

    private var isManagementMode = false
    private var lastClickedPosition: Int = -1

    class BookClubCommunityMemberViewHolder(
        val binding: FragmentCommunitymemberrvBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BookClubCommunityMemberViewHolder {
        val binding = FragmentCommunitymemberrvBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookClubCommunityMemberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookClubCommunityMemberViewHolder, position: Int) {
        val member = members[position]
        with(holder.binding) {
            communitymemberProfileIv.setImageResource(R.drawable.icon_profile)
            communitymemberNameTv.text = member.name
            communitymemberFloatTv.text = member.floatmsg
            communitymemberCrownIv.visibility = if (member.isLeader) View.VISIBLE else View.GONE

            // Management mode specific UI updates
            communitymemberCloseIv.visibility = if (isManagementMode) View.VISIBLE else View.GONE
            communitymemberFloatFl.visibility = if (isManagementMode) View.INVISIBLE else View.VISIBLE

            communitymemberCloseIv.setOnClickListener {
                lastClickedPosition = position
                onCloseClick(member)
            }
            root.setOnClickListener { onItemClick(member) }
        }
    }

    override fun getItemCount() = members.size

    fun setManagementMode(enabled: Boolean) {
        if (isManagementMode != enabled) {
            isManagementMode = enabled
            notifyDataSetChanged()
        }
    }

    fun removeLastClickedItem() {
        if (lastClickedPosition != -1) {
            members.removeAt(lastClickedPosition)
            notifyItemRemoved(lastClickedPosition)
            lastClickedPosition = -1
        }
    }
}