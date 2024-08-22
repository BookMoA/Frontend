package com.bookmoa.android.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SearchMemoResponse (
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("data") val data: List<SearchMemoData>
)


@Parcelize
data class SearchMemoData(

    @SerializedName("memoId") val memoid: Long=0,
    @SerializedName("bookId") val bookid: Long=0,
    @SerializedName("title") val title: String="",
    @SerializedName("coverImage") val img: String="",
    @SerializedName("writer") val writer: String="",
    @SerializedName("memo") val memo: String="",
    @SerializedName("createAt") val createAt: String="",



    ) : Parcelable