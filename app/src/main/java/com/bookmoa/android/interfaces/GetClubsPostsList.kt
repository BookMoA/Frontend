package com.bookmoa.android.interfaces

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetClubsPostsList {
    @GET("/clubs/posts/list")
    suspend fun getClubsPostsList(
        @Header("Authorization") token: String,
        @Query("clubId") clubId: Int,
        @Query("page") page: Int = 1
    ): ClubsPostsListResponse
}

data class ClubsPostsListResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("data") val data: ClubsPostsListData
)

data class ClubsPostsListData(
    @SerializedName("clubId") val clubId: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("postList") val postList: List<Post>
)

data class Post(
    @SerializedName("postId") val postId: Int,
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("context") val context: String,
    @SerializedName("createAt") val createAt: String,
    @SerializedName("updateAt") val updateAt: String
)
