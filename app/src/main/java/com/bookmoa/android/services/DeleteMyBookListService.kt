package com.bookmoa.android.services

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Query

interface DeleteMyBookListService {

    @DELETE("library/list")
    fun deleteBookListId(
        @Query("bookListIds") bookListIds: List<Int>
    ): Call<ResponseBody>
}
