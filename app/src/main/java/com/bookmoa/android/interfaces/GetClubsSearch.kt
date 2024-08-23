package com.bookmoa.android.interfaces

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetClubsSearch {
    @GET("/clubs/search")
    suspend fun searchClubs(
        @Header("Authorization") token: String,
        @Query("category") category: String = "name",
        @Query("word") word: String,
        @Query("page") page: Int = 1
    ): Response<GetClubsSearchResponse>
}

data class GetClubsSearchResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("data") val data: GetClubsSearchData
)

data class GetClubsSearchData(
    @SerializedName("category") val category: String,
    @SerializedName("word") val word: String,
    @SerializedName("page") val page: Int,
    @SerializedName("totalElements") val totalElements: Int,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("size") val size: Int,
    @SerializedName("clubList") val clubList: List<GetClubsSearchClub>
)

data class GetClubsSearchClub(
    @SerializedName("clubId") val clubId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("intro") val intro: String,
    @SerializedName("createAt") val createAt: String,
    @SerializedName("updateAt") val updateAt: String,
    @SerializedName("memberCount") val memberCount: Int,
    @SerializedName("postCount") val postCount: Int
)
