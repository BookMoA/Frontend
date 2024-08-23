package com.bookmoa.android.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface PostClubsMembers {
    @POST("/clubs/members")
    @Headers("Content-Type: application/json")
    fun createClubMember(
        @Header("Authorization") token: String,  // Token parameter added here
        @Body request: CreateClubMemberRequest
    ): Call<CreateClubMemberResponse>
}

data class CreateClubMemberRequest(
    val clubId: Int,
    val password: String
)

data class CreateClubMemberResponse(
    val status: String,
    val code: String,
    val result: Boolean,
    val description: String,
    val data: PostClubMemberData
)

data class PostClubMemberData(
    val clubMemberId: Int
)