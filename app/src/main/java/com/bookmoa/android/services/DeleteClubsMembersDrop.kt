package com.bookmoa.android.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.DELETE

interface DeleteClubsMembersDrop {
    @DELETE("/clubs/members/drop")
    @Headers("Content-Type: application/json")
    fun deleteMember(
        @Header("Authorization") token: String,  // Token parameter for authorization
        @Body request: DeleteMemberRequest
    ): Call<DeleteMemberResponse>
}

data class DeleteMemberRequest(
    val memberId: Int  // Request body parameter to specify the member to be dropped
)

data class DeleteMemberResponse(
    val status: String,
    val code: String,
    val result: Boolean,
    val description: String,
    val data: String
)
