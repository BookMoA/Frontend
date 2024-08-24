package com.bookmoa.android.services

import BookDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AladinBookDetailService {
    @GET("ItemLookUp.aspx")
    fun getBooksByISBN(
        @Query("ttbkey") apiKey: String, // Aladin API 키
        @Query("itemId") isbn13: String, // ISBN13
        @Query("itemIdType") itemIdType: String = "ISBN13", // 기본값은 ISBN13
        @Query("output") output: String = "js", // 출력 형식
        @Query("Version") version: String = "20131101" // API 버전
    ): Call<BookDetailResponse> // Aladin API에 맞는 응답 DTO
}