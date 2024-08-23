package com.bookmoa.android.services

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetClubsDetail {
    @GET("/clubs/detail")
    fun getClubDetail(
        // @Header("Authorization") token: String,
        @Query("clubId") clubId: Long
    ): Call<ClubDetailResponse>
}

data class ClubDetailResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("data") val data: ClubDetailData
)

data class ClubDetailData(
    @SerializedName("clubId") val clubId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("intro") val intro: String,
    @SerializedName("notice") val notice: String,
    @SerializedName("code") val code: String,
    @SerializedName("password") val password: String,
    @SerializedName("createAt") val createAt: String,
    @SerializedName("updateAt") val updateAt: String,
    @SerializedName("memberCount") val memberCount: Int
)