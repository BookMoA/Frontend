package com.bookmoa.android.services

import com.bookmoa.android.models.SearchBookResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AladinBookService {
    // get : 데이터 요청 시 반환 http
    // post : http body에 넣어 전달

    // 책 검색.
        // 책 검색.
        @GET("ItemSearch.aspx")
        fun getBooksByName(
            @Query("ttbkey") apiKey: String, // Aladin API 키
            @Query("Query") query: String, // 검색어
            @Query("QueryType") queryType: String = "Title", // 기본값은 Title
            @Query("MaxResults") maxResults: Int = 100, // 최대 결과 수
            @Query("start") start: Int = 1, // 검색 시작 위치
            @Query("SearchTarget") searchTarget: String = "Book", // 검색 대상
            @Query("output") output: String = "js", // 출력 형식
            @Query("Version") version: String = "20131101" // API 버전
        ): Call<SearchBookResponse> // Aladin API에 맞는 응답 DTO



    
}