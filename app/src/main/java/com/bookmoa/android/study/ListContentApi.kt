package com.bookmoa.android.study

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ListContentApi {

    @GET("library/list/{bookListId}")
    suspend fun getBookListById(@Path("bookListId") bookListId: Int,
                                @Header("Authorization") token: String): Response<ListContentResponse>
}