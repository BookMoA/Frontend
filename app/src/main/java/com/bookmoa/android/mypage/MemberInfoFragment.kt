package com.bookmoa.android.mypage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bookmoa.android.R
import com.bookmoa.android.adapter.MemberInfoAdapter
import com.bookmoa.android.databinding.FragmentMemberInfoBinding
import com.bookmoa.android.models.MemberInfoResponse
import com.bookmoa.android.services.ApiService
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberInfoFragment : Fragment() {

    private lateinit var binding: FragmentMemberInfoBinding
    private lateinit var adapter: MemberInfoAdapter
    private lateinit var api: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMemberInfoBinding.inflate(inflater, container, false)

        initRecyclerView()

        GlobalScope.launch {
            api = ApiService.createWithHeader(requireContext())
            api.adminInfo().enqueue(object: Callback<MemberInfoResponse> {
                override fun onResponse(
                    call: Call<MemberInfoResponse>,
                    response: Response<MemberInfoResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {MemberInfoResponse ->
                            Log.d("[MEMBER]", "MemeberInfo: $MemberInfoResponse")
                            Log.d("[MEMBER]", "멤버 정보 조회 성공")
                            adapter = MemberInfoAdapter(MemberInfoResponse.data)
                            binding.memeberRv.adapter = adapter
                        }
                    } else {
                        Log.d("[MEMBER]", "멤버 정보 조회 실패: ${response.errorBody()}")
                    }
                }

                override fun onFailure(call: Call<MemberInfoResponse>, t: Throwable) {
                    Log.d("[MEMBER]", "멤버 정보 통신 실패")
                }
            })
        }

        return binding.root
    }

    private fun initRecyclerView() {
        with(binding) {
            memeberRv.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }
        }
    }


}