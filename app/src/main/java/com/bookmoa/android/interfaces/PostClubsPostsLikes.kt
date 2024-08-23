package com.bookmoa.android.interfaces

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface PostClubsPostsLikes {
    @POST("/clubs/posts/likes")
    @Headers("Content-Type: application/json")
    fun createPostLike(
        @Header("Authorization") token: String,  // Token parameter for authorization
        @Body request: CreatePostLikeRequest
    ): Call<CreatePostLikeResponse>
}

data class CreatePostLikeRequest(
    val postId: Int
)

data class CreatePostLikeResponse(
    val status: String,
    val code: String,
    val result: Boolean,
    val description: String,
    val data: PostLikeData
)

data class PostLikeData(
    val postLikedId: Int
)
