package com.bookmoa.android.services


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface AddBookListSelectAnotherService {

    @POST("library/list/book/{bookListId}")
    fun postBookIds(
    @Header("Authorization") token: String,
    @Path("bookListId") bookListId: Int,
    @Body requestBody: BooksRequest
    ): Call<ResponseBody>
}

data class BooksRequest(
    val booksId: List<Int>
)