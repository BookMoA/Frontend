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
    @Body bookListIds: List<Int> // 선택된 ID 리스트를 전송
    ): Call<ResponseBody>
}