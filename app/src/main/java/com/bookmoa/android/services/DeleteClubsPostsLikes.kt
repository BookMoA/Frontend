package com.bookmoa.android.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.DELETE

interface DeleteClubsPostsLikes {
    @DELETE("/clubs/posts/likes")
    @Headers("Content-Type: application/json")
    fun deletePostLike(
        @Header("Authorization") token: String,  // Token parameter for authorization
        @Body request: DeletePostLikeRequest
    ): Call<DeletePostLikeResponse>
}

data class DeletePostLikeRequest(
    val postId: Int
)

data class DeletePostLikeResponse(
    val status: String,
    val code: String,
    val result: Boolean,
    val description: String,
    val data: String
)
