package com.bookmoa.android.study

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bookmoa.android.MainActivity
import com.bookmoa.android.databinding.FragmentStudyVp4Binding
import com.bookmoa.android.models.RecommendBookResponse
import com.bookmoa.android.services.ApiService
import com.bumptech.glide.Glide
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StudyVp4Fragment : Fragment() {

    lateinit var binding: FragmentStudyVp4Binding
    private lateinit var api: ApiService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyVp4Binding.inflate(inflater, container, false)

        binding.imgBanner4.setOnClickListener {
            (activity as MainActivity).switchFragment(RecommendFragment())
        }
        loadRecommendata()
        return binding.root
    }
    private fun loadRecommendata() {
        // Initialize the API service
        GlobalScope.launch {

            api = ApiService.createWithHeader(requireContext())

            // Make the network request
            api.getRecommendList().enqueue(object : Callback<RecommendBookResponse> {
                override fun onResponse(
                    call: Call<RecommendBookResponse>,
                    response: Response<RecommendBookResponse>
                ) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        if (apiResponse != null) {
                            val books = apiResponse.data?.books
                            if (books != null && books.isNotEmpty()) {
                                // Update UI on the main thread
                                Glide.with(requireContext())
                                    .load(books[3].coverImage)
                                    .centerCrop()
                                    .into(binding.imgBanner4)
                            } else {
                                Toast.makeText(context, "데이터가 없습니다.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(context, "데이터를 가져오는 중 오류가 발생했습니다.", Toast.LENGTH_SHORT)
                            .show()
                        Log.d(
                            "[LIST]",
                            "Top List 오류 발생: ${response.code()}, message: ${response.message()}"
                        )
                    }
                }

                override fun onFailure(call: Call<RecommendBookResponse>, t: Throwable) {
                    Toast.makeText(context, "네트워크 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
                    Log.d("[LIST]", "Top List - 통신 실패: ${t.message}")
                }
            })
        }
    }
}