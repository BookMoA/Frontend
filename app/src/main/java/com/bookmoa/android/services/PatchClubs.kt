package com.bookmoa.android.services

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.PATCH

interface PatchClubs {
    @PATCH("/clubs")
    suspend fun patchClubs(
        @Header("Authorization") token: String,
        @Body requestBody: PatchClubsRequest
    ): PatchClubsResponse
}

data class PatchClubsRequest(
    @SerializedName("clubId") val clubId: Int,
    @SerializedName("intro") val intro: String,
    @SerializedName("notice") val notice: String
)

data class PatchClubsResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("data") val data: ClubData
)

data class ClubData(
    @SerializedName("clubId") val clubId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("intro") val intro: String,
    @SerializedName("notice") val notice: String,
    @SerializedName("code") val code: String,
    @SerializedName("password") val password: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)
