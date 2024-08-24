package com.bookmoa.android.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface PostClubs {
    @POST("/clubs")
    @Headers("Content-Type: application/json")
    fun createClub(
        // @Header("Authorization") token: String,  // Token parameter added here
        @Body request: CreateClubRequest
    ): Call<CreateClubResponse>
}

data class CreateClubRequest(
    val name: String,
    val intro: String,
    val notice: String,
    val password: String
)

data class CreateClubResponse(
    val status: String,
    val code: String,
    val result: Boolean,
    val description: String,
    val data: PostClubsData
)

data class PostClubsData(
    val clubId: Int
)
