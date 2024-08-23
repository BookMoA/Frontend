package com.bookmoa.android.group

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bookmoa.android.MainActivity
import com.bookmoa.android.TokenManager
import com.bookmoa.android.databinding.FragmentGroupBinding
import com.bookmoa.android.interfaces.ClubApi
import com.bookmoa.android.interfaces.GetClubData
import com.bookmoa.android.interfaces.GetClubs
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GroupFragment : Fragment() {

    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!
    private lateinit var tokenManager: TokenManager
    private var isClubJoined: Boolean = false
    private var clubData: GetClubData? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tokenManager = TokenManager(requireContext())

        binding.groupSearchIv.setOnClickListener {
            (activity as MainActivity).switchFragment(OutSearchFragment())
        }

        binding.groupMakeFl.setOnClickListener {
            (activity as MainActivity).switchFragment(MakeFragment())
        }

        binding.groupRightIv.setOnClickListener {
            val communityFragment = CommunityFragment().apply {
                arguments = Bundle().apply {
                    clubData?.let { data ->
                        putInt("clubId", data.clubId)
                        putString("name", data.name)
                        putString("intro", data.intro)
                    }
                }
            }
            (activity as MainActivity).switchFragment(communityFragment)
        }

        fetchClubData()
    }

    private fun fetchClubData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val clubApi = retrofit.create(ClubApi::class.java)
        val token = tokenManager.getToken()
        val bearerToken = "Bearer $token"

        if (token.isNullOrEmpty()) {
            Log.d("GroupFragment", "Token is null or empty")
            return
        }

        val call = clubApi.getClubs(bearerToken)
        call.enqueue(object : Callback<GetClubs> {
            override fun onResponse(call: Call<GetClubs>, response: Response<GetClubs>) {
                Log.d("GroupFragment", "Clubs data fetched, Response code: ${response.code()}")
                if (response.isSuccessful) {
                    val clubsResponse = response.body()
                    clubsResponse?.let {
                        updateUIWithClubData(it.data)
                        isClubJoined = it.data?.clubId != null && it.data?.name != null && it.data?.intro != null
                        Log.d("GroupFragment", "Clubs data fetched successfully")

                        // Fetch complete, now update the ViewPager adapter with the new isClubJoined value
                        setupViewPager()
                    }
                } else {
                    Log.d("GroupFragment", "Clubs data fetch failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<GetClubs>, t: Throwable) {
                Log.d("GroupFragment", "Clubs data fetch failed with error: ${t.message}")
            }
        })
    }

    private fun setupViewPager() {
        val viewPager: ViewPager2 = binding.groupVp
        val tabLayout: TabLayout = binding.groupTl

        val adapter = BookClubVP2Adapter(requireActivity(), isClubJoined)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "신규"
                1 -> "활동순"
                2 -> "마감 임박"
                else -> null
            }
        }.attach()
    }

    private fun updateUIWithClubData(clubData: GetClubData?) {
        this.clubData = clubData
        if (clubData != null && clubData.name != null && clubData.intro != null) {
            binding.groupMakeCv.visibility = View.GONE
            binding.groupcvCv.visibility = View.VISIBLE
            clubData.let {
                binding.groupNameTv.text = it.name
                binding.groupIntroTv.text = it.intro
            }
        } else {
            binding.groupMakeCv.visibility = View.VISIBLE
            binding.groupcvCv.visibility = View.GONE
        }
    }

    companion object {
        fun newInstance() = GroupFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


class BookClubVP2Adapter(fragmentActivity: FragmentActivity, private val isClubJoined: Boolean): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return GroupRvFragment.newInstance(position, isClubJoined)
    }
}