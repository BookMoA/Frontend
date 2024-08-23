package com.bookmoa.android.group

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bookmoa.android.MainActivity
import com.bookmoa.android.services.TokenManager
import com.bookmoa.android.databinding.FragmentCommunityBinding
import com.google.android.material.tabs.TabLayoutMediator

class CommunityFragment : Fragment() {
    private var _binding: FragmentCommunityBinding? = null
    private val binding get() = _binding!!
    private lateinit var tokenManager: TokenManager

    private var clubId: Int? = null
    private var clubName: String? = null
    private var clubIntro: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            clubId = it.getInt("clubId")
            clubName = it.getString("name")
            clubIntro = it.getString("intro")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tokenManager = TokenManager(requireContext())

        binding.communityTitleTv.text = clubName
        binding.communityDescriptionTv.text = clubIntro

        binding.communityBackIv.setOnClickListener {
            (activity as MainActivity).switchFragment(GroupFragment())
        }

        binding.communitySearchIv.setOnClickListener {
            (activity as MainActivity).switchFragment(InSearchFragment())
        }

        binding.communityWriteIv.setOnClickListener {
            val intent = Intent(requireContext(), WriteActivity::class.java)
            startActivity(intent)
        }

        val adapter = ViewPagerAdapter(requireActivity())

        // Create the Feed fragment with the clubId passed as argument
        val feedFragment = CommunityFeedFragment().apply {
            arguments = Bundle().apply {
                putInt("clubId", clubId ?: 0)
                putString("clubName", clubName)
                putString("clubIntro", clubIntro)
            }
        }

        val memberFragment = CommunityMemberFragment().apply {
            arguments = Bundle().apply {
                putInt("clubId", clubId ?: 0)
            }
        }

        val manageFragment = CommunityManageFragment().apply {
            arguments = Bundle().apply {
                putInt("clubId", clubId ?: 0)
            }
        }


        adapter.addFragment(feedFragment, "피드")
        adapter.addFragment(CommunityStatisticFragment(), "통계")
        adapter.addFragment(memberFragment, "멤버")
        adapter.addFragment(manageFragment, "관리")

        binding.communityVp.adapter = adapter

        TabLayoutMediator(binding.communityTl, binding.communityVp) { tab, position ->
            tab.text = when (position) {
                0 -> "피드"
                1 -> "통계"
                2 -> "멤버"
                3 -> "관리"
                else -> null
            }
        }.attach()

        fetchClubMembers()
    }

    private fun fetchClubMembers(){

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragmentList = mutableListOf<Fragment>()
    private val fragmentTitleList = mutableListOf<String>()

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        fragmentTitleList.add(title)
    }

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}