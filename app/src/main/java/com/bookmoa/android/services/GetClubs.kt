package com.bookmoa.android.services

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Header

interface GetClubs {
    @GET("/clubs")
    suspend fun getClubs(
        @Header("Authorization") token: String
    ): GetClubsResponse
}

data class GetClubsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("data") val data: GetClubsData
)

data class GetClubsData(
    @SerializedName("memeberId") val memeberId: Int,
    @SerializedName("clubId") val clubId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("intro") val intro: String,
    @SerializedName("reader") val reader: Boolean
)
