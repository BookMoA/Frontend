package com.bookmoa.android.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RecommendBookDetailResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: String,
    @SerializedName("description") val description: String,
    @SerializedName("data") val data: RecommendBookDetailData
)

@Parcelize
data class RecommendBookDetailData(
    @SerializedName("bookId") val bookId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("writer") val writer: String,
    @SerializedName("description") val description: String,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("isbn") val isbn: String,
    @SerializedName("page") val page: Int,
    @SerializedName("coverImage") val coverImage: String?,

    ): Parcelable

