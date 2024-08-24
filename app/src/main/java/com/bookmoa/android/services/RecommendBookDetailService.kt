package com.bookmoa.android.services

import com.bookmoa.android.models.BookCheckResponse
import com.bookmoa.android.models.RecommendBookDetailResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecommendBookDetailService {

    @GET("books/{bookId}")
    fun RecommendBookDetail(@Path("bookId") bookId: Int): Call<RecommendBookDetailResponse>
}