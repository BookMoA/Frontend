package com.bookmoa.android.services

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

interface GetClubsPostsComments {
    @GET("/clubs/posts/comments")
    suspend fun getComments(
        @Query("postId") postId: Long,
        @Query("page") page: Int = 1
    ): CommentsResponse
}

data class CommentsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("data") val data: CommentsData
)

data class CommentsData(
    @SerializedName("postId") val postId: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("commentList") val commentList: List<Comment>
)

data class Comment(
    @SerializedName("commentId") val commentId: Int,
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("context") val context: String,
    @SerializedName("createAt") val createAt: String,
    @SerializedName("updateAt") val updateAt: String
)
