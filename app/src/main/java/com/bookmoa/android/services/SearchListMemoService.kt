package com.bookmoa.android.services

import com.bookmoa.android.models.SearchBookListResponse
import com.bookmoa.android.models.SearchMemoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface SearchListMemoService {

    @GET("search/list")
    fun getBookList(
        @Query("title") query: String, // 검색어
    ): Call<SearchBookListResponse>

    @GET("search/memo")
    fun getBookMemos(
        @Query("keyword") query: String, // 검색어
    ): Call<SearchMemoResponse>
}