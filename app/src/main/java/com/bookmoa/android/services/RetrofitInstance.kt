package com.bookmoa.android.services

import com.google.gson.internal.GsonBuildConfig
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

    val bookCheckApi: BookDBCheckService by lazy {
        retrofit.create(BookDBCheckService::class.java)
    }

    val bookEntryApi: BookEntryService by lazy {
        retrofit.create(BookEntryService::class.java)
    }
    val searchListApi: SearchListMemoService by lazy {
        retrofit.create(SearchListMemoService::class.java)
    }

}