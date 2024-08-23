package com.bookmoa.android.interfaces

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface GetClubsMembers {
    @GET("/clubs/members")
    suspend fun getClubsMembers(
        @Header("Authorization") token: String,
        @Query("clubId") clubId: Int
    ): ClubsMembersResponse
}

data class ClubsMembersResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("data") val data: List<MemberData>
)

data class MemberData(
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("reader") val reader: Boolean,
    @SerializedName("statusMessage") val statusMessage: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String
)
