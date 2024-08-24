package com.bookmoa.android.services

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetClubsPosts {
    @GET("/clubs/posts")
    suspend fun getClubsPosts(
        @Header("Authorization") token: String,
        @Query("postId") postId: Int
    ): ClubsPostsResponse
}

data class ClubsPostsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("data") val data: PostData
)

data class PostData(
    @SerializedName("postId") val postId: Int,
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("context") val context: String,
    @SerializedName("commentCount") val commentCount: Int,
    @SerializedName("likeCount") val likeCount: Int,
    @SerializedName("createAt") val createAt: String,
    @SerializedName("updateAt") val updateAt: String
)
