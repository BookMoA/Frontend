package com.bookmoa.android.services

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetClubsRecommend {
    @GET("/clubs/recommend")
    suspend fun getRecommendedClubs(
        @Header("Authorization") token: String,
        @Query("category") category: String = "new",
        @Query("page") page: Int = 1
    ): Response<GetClubsRecommendResponse>
}

data class GetClubsRecommendResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("data") val data: GetClubsRecommendData
)

data class GetClubsRecommendData(
    @SerializedName("category") val category: String,
    @SerializedName("page") val page: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("clubList") val clubList: List<GetClubsRecommendClub>
)

data class GetClubsRecommendClub(
    @SerializedName("clubId") val clubId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("intro") val intro: String,
    @SerializedName("createAt") val createAt: String,
    @SerializedName("updateAt") val updateAt: String,
    @SerializedName("memberCount") val memberCount: Int,
    @SerializedName("postCount") val postCount: Int
)