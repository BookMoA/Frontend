package com.bookmoa.android.services


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface addBookListAnother {

    @POST("library/list/{bookListId}/another")
    fun postBookId(
        //@Header("Authorization") token: String,
        @Path("bookListId") bookListId: Int,
       ): Call<ResponseBody>
}