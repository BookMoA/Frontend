package com.bookmoa.android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bookmoa.android.R
import com.bookmoa.android.databinding.FragmentCommunitymemberrvBinding

data class CommunityMemberItems(
    val memberId: Int,
    val name: String,
    val floatmsg: String,
    val isLeader: Boolean,
    val float: Boolean
)

class CommunityMemberFragmentAdapter(
    private val members: MutableList<CommunityMemberItems>,
    private val myMemberId: Int?,
    private val onItemClick: (CommunityMemberItems) -> Unit,
    private val onCloseClick: (CommunityMemberItems) -> Unit
) : RecyclerView.Adapter<CommunityMemberFragmentAdapter.BookClubCommunityMemberViewHolder>() {

    private var isManagementMode = false

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

            // Hide the close icon if the member is a leader
            communitymemberCloseIv.visibility = if (isManagementMode && !member.isLeader) View.VISIBLE else View.GONE

            // Hide or show the float message layout based on the float property
            communitymemberFloatFl.visibility = if (member.float) View.VISIBLE else View.INVISIBLE

            communitymemberCloseIv.setOnClickListener {
                onCloseClick(member)
            }

            // Enable click only if it's not management mode and the memberId matches myMemberId
            if (!isManagementMode && member.memberId == myMemberId) {
                root.setOnClickListener { onItemClick(member) }
            } else {
                root.setOnClickListener(null) // Disable click listener for other cases
            }
        }
    }





    override fun getItemCount() = members.size

    fun setManagementMode(enabled: Boolean) {
        if (isManagementMode != enabled) {
            isManagementMode = enabled
            notifyDataSetChanged()
        }
    }
}