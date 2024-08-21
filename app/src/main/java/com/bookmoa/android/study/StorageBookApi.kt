package com.bookmoa.android.study

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface StorageBookApi {
    @GET("library/book")
    suspend fun getBooks(
        @Header("Authorization") auth: String,
        @Query("category") category: String,
        @Query("sortBy") sortBy: String = "relevance",
        @Query("page") page: Int = 1
    ): Response<StorageBookResponse>
}