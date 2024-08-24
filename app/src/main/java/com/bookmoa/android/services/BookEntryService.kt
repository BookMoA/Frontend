package com.bookmoa.android.services


import com.bookmoa.android.models.BookEntryResponse
import com.bookmoa.android.models.NewBookAladin
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface BookEntryService {

    @POST("books/aladin")
    suspend fun createBook(
       // @Header("Authorization") token: String,
        @Body newBook: NewBookAladin
    ): Response<BookEntryResponse>

}