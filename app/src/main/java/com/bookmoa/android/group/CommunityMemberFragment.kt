package com.bookmoa.android.group

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.bookmoa.android.R
import com.bookmoa.android.adapter.CommunityMemberFragmentAdapter
import com.bookmoa.android.adapter.CommunityMemberItems
import com.bookmoa.android.databinding.FragmentCommunitymembervpBinding

class CommunityMemberFragment : Fragment() {
    private val binding by lazy { FragmentCommunitymembervpBinding.inflate(layoutInflater) }
    private lateinit var adapter: CommunityMemberFragmentAdapter
    private var isManagementMode = false
    private var memberList = mutableListOf<CommunityMemberItems>()

    private val expelLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            adapter.removeLastClickedItem()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupToggleButton()
    }

    private fun setupRecyclerView() {
        memberList = mutableListOf(
            CommunityMemberItems("닉네임", "ㅎㅇ", true, true),
            CommunityMemberItems("너", "", true, false),
            CommunityMemberItems("나", "ㅎㅇㅎㅇㅎㅇ", false, true),
            CommunityMemberItems("우리", "", false, false),
            CommunityMemberItems("너", "", true, false),
            CommunityMemberItems("나", "ㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇ", false, true),
            CommunityMemberItems("우리", "", false, false),
            CommunityMemberItems("너", "", true, false),
            CommunityMemberItems("나", "ㅎㅇㅎㅇㅎㅇㅎㅇㅎㅇ", false, true),
            CommunityMemberItems("우리", "", false, false)
        )

        adapter = CommunityMemberFragmentAdapter(
            memberList,
            onItemClick = { member ->
                val intent = Intent(requireContext(), ProfileActivity::class.java).apply {
                    putExtra("name", member.name)
                    putExtra("floatMsg", member.floatmsg)
                }
                startActivity(intent)
            },
            onCloseClick = { member ->
                val intent = Intent(requireActivity(), DialogExpelActivity::class.java).apply{
                    putExtra("memberName", member.name)
                }
                expelLauncher.launch(intent)
            }
        )

        binding.communitymemberRv.apply {
            layoutManager = GridLayoutManager(context, 3)
            this.adapter = this@CommunityMemberFragment.adapter
        }
    }

    private fun setupToggleButton() {
        val originalBackground = ContextCompat.getDrawable(requireContext(), R.drawable.background_circlesub)
        val toggledBackground = VectorDrawableCompat.create(resources, R.drawable.background_circlemain, requireContext().theme)

        binding.communitymembervpLl.setOnClickListener {
            isManagementMode = !isManagementMode
            adapter.setManagementMode(isManagementMode)
            binding.communitymembervpLl.background = if (isManagementMode) toggledBackground else originalBackground
            binding.communitymembervpMemberTv.text = if (isManagementMode) "멤버 관리 완료" else "멤버관리"
            binding.communitymembervpSettingIv.visibility = if (isManagementMode) View.GONE else View.VISIBLE
        }
    }
}