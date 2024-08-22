package com.bookmoa.android.services


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

    val storageListapi: StorageListApi by lazy {
        retrofit.create(StorageListApi::class.java)
    }
    val storageBookapi: StorageBookApi by lazy {
        retrofit.create(StorageBookApi::class.java)
    }

    val postAnotherBookIdapi: addBookListAnother by lazy {
        retrofit.create(addBookListAnother::class.java)
    }

    val postAnotherBookselectapi: AddBookListSelectAnotherService by lazy {
        retrofit.create(AddBookListSelectAnotherService::class.java)
    }
}


