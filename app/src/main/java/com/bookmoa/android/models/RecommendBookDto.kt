package com.bookmoa.android.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class RecommendBookResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: String,
    @SerializedName("data") val data: RecommendBookInfo
)

data class RecommendBookInfo(
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("books") val books: List<RecommendBookData>
)

@Parcelize
data class RecommendBookData(
    @SerializedName("bookId") val bookId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("writer") val writer: String,
    @SerializedName("coverImage") val coverImage: String?,

): Parcelable