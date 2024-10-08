package com.bookmoa.android.services

import com.bookmoa.android.models.StorageListResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface StorageListApi {

    @GET("library/list")
    fun getStorageList(
        // @Header("Authorization") auth: String,
        @Query("page") page: Int = 1
    ): Call<StorageListResponse>
}