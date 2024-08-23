package com.bookmoa.android.group

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.services.TokenManager
import com.bookmoa.android.adapter.GroupRvFragmentAdapter
import com.bookmoa.android.adapter.GroupRvItems
import com.bookmoa.android.databinding.FragmentGroupvpBinding
import com.bookmoa.android.services.ApiService
import com.bookmoa.android.services.GetClubsRecommend
import com.bookmoa.android.services.GetClubsRecommendResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GroupRvFragment: Fragment() {
    private var _binding: FragmentGroupvpBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: GroupRvFragmentAdapter
    private lateinit var tokenManager: TokenManager
    private var position: Int = 0
    private var isClubJoined: Boolean = false

    private lateinit var api: ApiService

    companion object {
        private const val ARG_POSITION = "position"
        private const val ARG_IS_CLUB_JOINED = "isClubJoined"

        fun newInstance(position: Int, isClubJoined: Boolean): GroupRvFragment {
            val fragment = GroupRvFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            args.putBoolean(ARG_IS_CLUB_JOINED, isClubJoined)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            position = it.getInt(ARG_POSITION)
            isClubJoined = it.getBoolean(ARG_IS_CLUB_JOINED)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGroupvpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.groupRv.layoutManager = LinearLayoutManager(context)

        tokenManager = TokenManager()

        fetchRecommendedClubs()
    }

    private fun fetchRecommendedClubs() {

        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())

            api.getRecommendedClubs().enqueue(object: Callback<GetClubsRecommendResponse> {
                override fun onResponse(
                    call: Call<GetClubsRecommendResponse>,
                    response: Response<GetClubsRecommendResponse>
                ) {
                    if (response.isSuccessful && response.body()?.result == true) {
                        Log.d("GroupRvFragment", "Response successful")
                        response.body()?.data?.clubList?.let { clubList ->
                            val sortedList = when (position) {
                                0 -> clubList.sortedByDescending { it.createAt }
                                1 -> clubList.sortedByDescending { it.postCount }
                                2 -> clubList.sortedBy { Math.abs(20 - it.memberCount) }
                                else -> clubList
                            }

                            val groupRvItems = sortedList.map { club ->
                                GroupRvItems(
                                    club.clubId,
                                    club.name,
                                    club.intro,
                                    club.createAt,
                                    club.updateAt,
                                    club.memberCount,
                                    club.postCount
                                )
                            }
                            adapter = GroupRvFragmentAdapter(groupRvItems) { clubId ->
                                val dialogFragment = DialogJoinFragment.newInstance(clubId, isClubJoined)
                                dialogFragment.show(parentFragmentManager, "DialogJoinFragment")
                            }
                            binding.groupRv.adapter = adapter
                        }
                    } else {
                        Log.d("GroupRvFragment", "Error: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<GetClubsRecommendResponse>, t: Throwable) {
                    Log.d("GroupRvFragment", "Exception during network call")
                }

            })
        }

        /*
        val retrofit = Retrofit.Builder()
            .baseUrl("https://bookmoa.shop")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val clubApi = retrofit.create(GetClubsRecommend::class.java)
        val token = tokenManager.getToken()
        val bearerToken = "Bearer $token"
        if (token.isNullOrEmpty()) {
            Log.d("GroupFragment", "Token is null or empty")
            return
        }

        lifecycleScope.launch {
            try {
                val response = clubApi.getRecommendedClubs()
                if (response.isSuccessful && response.body()?.result == true) {
                    Log.d("GroupRvFragment", "Response successful")
                    response.body()?.data?.clubList?.let { clubList ->
                        val sortedList = when (position) {
                            0 -> clubList.sortedByDescending { it.createAt }
                            1 -> clubList.sortedByDescending { it.postCount }
                            2 -> clubList.sortedBy { Math.abs(20 - it.memberCount) }
                            else -> clubList
                        }

                        val groupRvItems = sortedList.map { club ->
                            GroupRvItems(
                                club.clubId,
                                club.name,
                                club.intro,
                                club.createAt,
                                club.updateAt,
                                club.memberCount,
                                club.postCount
                            )
                        }
                        adapter = GroupRvFragmentAdapter(groupRvItems) { clubId ->
                            val dialogFragment = DialogJoinFragment.newInstance(clubId, isClubJoined)
                            dialogFragment.show(parentFragmentManager, "DialogJoinFragment")
                        }
                        binding.groupRv.adapter = adapter
                    }
                } else {
                    Log.d("GroupRvFragment", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("GroupRvFragment", "Exception during network call", e)
            }
        }

         */
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}