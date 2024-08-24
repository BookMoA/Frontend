package com.bookmoa.android.services

import com.bookmoa.android.models.RecommendBookResponse
import retrofit2.Call
import retrofit2.http.GET

interface RecommendBookService {
    @GET("library/recommend")
    fun getRecommendList(
        // @Header("Authorization") auth: String,
    ): Call<RecommendBookResponse>
}