package com.bookmoa.android.services

import com.bookmoa.android.models.StorageBookResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface StorageBookApi {
    @GET("library/book")
    fun getBooks(
        // @Header("Authorization") auth: String,
        @Query("category") category: String,
        @Query("sortBy") sortBy: String = "relevance",
        @Query("page") page: Int = 1
    ): Call<StorageBookResponse>
}