package com.bookmoa.android.group

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.bookmoa.android.MainActivity
import com.bookmoa.android.R
import com.bookmoa.android.services.TokenManager
import com.bookmoa.android.adapter.CommunityMemberFragmentAdapter
import com.bookmoa.android.adapter.CommunityMemberItems
import com.bookmoa.android.databinding.FragmentCommunitymembervpBinding
import com.bookmoa.android.services.GetClubs
import com.bookmoa.android.services.GetClubsMembers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class CommunityMemberFragment : Fragment() {
    private val binding by lazy { FragmentCommunitymembervpBinding.inflate(layoutInflater) }
    private lateinit var adapter: CommunityMemberFragmentAdapter
    private var isManagementMode = false
    private var memberList = mutableListOf<CommunityMemberItems>()
    private var isReader = false // 사용자가 리더인지 여부를 저장하는 변수
    private var myMemberId: Int? = null // Store the user's memberId
    private lateinit var tokenManager: TokenManager
    private var clubId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Retrieve the clubId from the arguments
        arguments?.let {
            clubId = it.getInt("clubId", 0)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tokenManager = TokenManager(requireContext())
        setupRecyclerView()
        setupToggleButton()
        checkLeaderStatus()
        fetchMyClub()
        fetchMembersData()
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
                    myMemberId = response.data.memeberId
                    isReader = response.data.reader

                    Log.d("CommunityMemberFragment", "Member ID: $myMemberId, Reader: $isReader")
                    // 필요한 다른 작업 수행
                } else {
                    Log.e("CommunityMemberFragment", "Failed to fetch clubs: ${response.description}")
                }
            } catch (e: Exception) {
                Log.e("CommunityMemberFragment", "Error fetching clubs", e)
            }
        }
    }


    private fun fetchMembersData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val clubApi = retrofit.create(GetClubsMembers::class.java)
        val token = tokenManager.getToken()
        if (token.isNullOrEmpty()) {
            Log.e("CommunityMemberFragment", "Token is null or empty")
            showErrorMessage("Authentication error. Please log in again.")
            return
        }

        val bearerToken = "Bearer $token"

        lifecycleScope.launch {
            try {
                Log.d("CommunityMemberFragment", "Fetching members data...")
                val response = clubApi.getClubsMembers(bearerToken, clubId)
                Log.d("CommunityMemberFragment", "API Response: $response")

                if (response.result) {
                    memberList.clear()
                    memberList.addAll(response.data.map { memberData ->
                        val floatmsg = memberData.statusMessage ?: ""
                        CommunityMemberItems(
                            memberId = memberData.memberId,
                            name = memberData.nickname,
                            floatmsg = floatmsg,
                            isLeader = memberData.reader,
                            float = floatmsg.isNotEmpty() // Set float based on whether floatmsg is empty
                        )
                    })
                    Log.d("CommunityMemberFragment", "Members fetched successfully. Count: ${memberList.size}")
                    adapter.notifyDataSetChanged()
                    checkLeaderStatus()
                } else {
                    Log.e("CommunityMemberFragment", "Failed to fetch members: ${response.description}")
                    showErrorMessage("Failed to fetch members: ${response.description}")
                }
            } catch (e: Exception) {
                Log.e("CommunityMemberFragment", "Error fetching members", e)
                when (e) {
                    is IOException -> showErrorMessage("Network error. Please check your internet connection.")
                    is HttpException -> {
                        val errorBody = e.response()?.errorBody()?.string()
                        Log.e("CommunityMemberFragment", "HTTP Error: ${e.code()}, Error Body: $errorBody")
                        showErrorMessage("Server error: ${e.code()}. Please try again later.")
                    }
                    else -> showErrorMessage("An unexpected error occurred. Please try again.")
                }
            }
        }
    }
    private fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    private fun setupRecyclerView() {
        adapter = CommunityMemberFragmentAdapter(
            memberList,
            myMemberId,  // myMemberId를 전달합니다.
            onItemClick = { member ->
                if (member.memberId == myMemberId) {  // myMemberId와 비교합니다.
                    val fragment = ProfileFragment().apply {
                        arguments = Bundle().apply {
                            putInt("memberId", member.memberId)
                            putString("name", member.name)
                            putString("floatMsg", member.floatmsg)
                        }
                    }
                    (activity as MainActivity).switchFragment(fragment)
                }
            },
            onCloseClick = { member ->
                showExpelDialog(member)
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

    private fun showExpelDialog(member: CommunityMemberItems) {
        val dialogFragment = DialogExpelFragment().apply {
            arguments = Bundle().apply {
                putString("memberName", member.name)
                putInt("memberId", member.memberId) // Pass the memberId to the DialogExpelFragment
            }
            setConfirmClickListener {
                removeMember(member)
            }
        }
        dialogFragment.show(parentFragmentManager, "DialogExpel")
    }

    private fun removeMember(member: CommunityMemberItems) {
        val position = memberList.indexOf(member)
        if (position != -1) {
            memberList.removeAt(position)
            adapter.notifyItemRemoved(position)
        }
    }

    private fun checkLeaderStatus() {
        // Show the management layout only if the user is a leader
        binding.communitymembervpLl.visibility = if (isReader) View.VISIBLE else View.GONE
    }

}