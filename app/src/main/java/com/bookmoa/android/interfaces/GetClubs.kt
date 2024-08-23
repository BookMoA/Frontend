package com.bookmoa.android.interfaces

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ClubApi {
    @GET("/clubs")
    fun getClubs(@Header("Authorization") token: String): Call<GetClubs>
}

data class GetClubs(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("data") val data: GetClubData
)

data class GetClubData(
    @SerializedName("clubId") val clubId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("intro") val intro: String
)