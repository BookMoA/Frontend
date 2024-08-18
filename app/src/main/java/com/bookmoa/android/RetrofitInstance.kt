package com.bookmoa.android

import com.bookmoa.android.study.ListContentApi
import com.bookmoa.android.study.ListTop10Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://bookmoa.shop/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val listTop10api: ListTop10Api by lazy {
        retrofit.create(ListTop10Api::class.java)
    }

    val listcontentapi: ListContentApi by lazy {
        retrofit.create(ListContentApi::class.java)
    }
}