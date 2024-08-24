package com.bookmoa.android.services

import com.bookmoa.android.models.BookCheckResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface BookDBCheckService {
    @GET("library/book/db")
    suspend fun checkBook(
       // @Header("Authorization") token: String,
        @Query("isbn") isbn: String
    ): Response<BookCheckResponse>
}