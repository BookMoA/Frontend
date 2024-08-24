package com.bookmoa.android.group

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.R
import com.bookmoa.android.adapter.NextLeaderFragmentAdapter
import com.bookmoa.android.adapter.NextLeaderItems
import com.bookmoa.android.databinding.FragmentNextleaderBinding
import com.bookmoa.android.services.GetClubs
import com.bookmoa.android.services.GetClubsMembers
import com.bookmoa.android.services.TokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NextLeaderFragment: Fragment(), NextLeaderFragmentAdapter.OnSelectChangeListener {
    private var _binding: FragmentNextleaderBinding? = null
    private val binding get() = _binding!!
    private lateinit var tokenManager: TokenManager
    private var isReader = false // 사용자가 리더인지 여부를 저장하는 변수
    private var memberId: Int? = null

    private lateinit var nextleaderAdapter: NextLeaderFragmentAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNextleaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tokenManager = TokenManager(requireContext())

        val clubId = arguments?.getInt("clubId", 0)

        binding.nextleaderRv.layoutManager = GridLayoutManager(context, 3)

        nextleaderAdapter = NextLeaderFragmentAdapter(emptyList(), this)
        binding.nextleaderRv.adapter = nextleaderAdapter

        binding.nextleaderBackIv.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        fetchMyClub()
        if (clubId != null && clubId > 0) {
            fetchMembersData(clubId)
        } else {
            Log.e("NextLeaderFragment", "Invalid clubId provided")
        }

    }

    override fun onSelectionChange(isSelected: Boolean) {
        // Change the src of nextleader_selected_iv based on the selection state
        if (isSelected) {
            binding.nextleaderSelectedIv.setImageResource(R.drawable.icon_selectmain)
        } else {
            binding.nextleaderSelectedIv.setImageResource(R.drawable.icon_select) // Revert back to the default if not selected
        }
    }

    private fun fetchMyClub() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val clubApi = retrofit.create(GetClubs::class.java)
        val token = tokenManager.getToken()
        val bearerToken = "Bearer $token"

        if (token.isNullOrEmpty()) {
            Log.d("CommunityMemberFragment", "Token is null or empty")
            return
        }

        // 코루틴 스코프에서 네트워크 호출을 실행
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = clubApi.getClubs(bearerToken)

                if (response.result) {
                    val memberId = response.data.memeberId
                    val reader = response.data.reader

                    Log.d("CommunityMemberFragment", "Member ID: $memberId, Reader: $reader")
                    // 필요한 다른 작업 수행
                } else {
                    Log.e("CommunityMemberFragment", "Failed to fetch clubs: ${response.description}")
                }
            } catch (e: Exception) {
                Log.e("CommunityMemberFragment", "Error fetching clubs", e)
            }
        }
    }

    private fun fetchMembersData(clubId: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val clubApi = retrofit.create(GetClubsMembers::class.java)
        val token = tokenManager.getToken()
        if (token.isNullOrEmpty()) {
            return
        }

        val bearerToken = "Bearer $token"

        lifecycleScope.launch {
            try {
                val response = clubApi.getClubsMembers(bearerToken, clubId)
                if (response.result) {
                    // Update RecyclerView with fetched data
                    val nextLeaderItems = response.data.map { member ->
                        NextLeaderItems(
                            isLeader = member.reader,
                            profile = R.drawable.icon_profile, // Assuming all profiles use the same drawable, replace with actual logic if needed
                            name = member.nickname,
                            isDetail = member.memberId == memberId // Set based on your conditions
                        )
                    }

                    nextleaderAdapter.updateItems(nextLeaderItems)
                } else {
                    Log.e("NextLeaderFragment", "Failed to fetch members: ${response.description}")
                }
            } catch (e: Exception) {
                Log.e("NextLeaderFragment", "Error fetching members", e)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}