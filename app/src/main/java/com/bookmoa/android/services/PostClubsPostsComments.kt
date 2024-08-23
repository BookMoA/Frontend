package com.bookmoa.android.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface PostClubsPostsComments {
    @POST("/clubs/posts/comments")
    @Headers("Content-Type: application/json")
    fun createComment(
        @Header("Authorization") token: String,  // Token parameter added here
        @Body request: CreateCommentRequest
    ): Call<CreateCommentResponse>
}

data class CreateCommentRequest(
    val postId: Int,
    val context: String
)

data class CreateCommentResponse(
    val status: String,
    val code: String,
    val result: Boolean,
    val description: String,
    val data: PostCommentData
)

data class PostCommentData(
    val postCommentId: Int
)
