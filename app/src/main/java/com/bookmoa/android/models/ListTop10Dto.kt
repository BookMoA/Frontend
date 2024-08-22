package com.bookmoa.android.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


data class ListTop10Response(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("data") val data: ListTop10BookListsData?
)

data class ListTop10BookListsData(
    @SerializedName("updatedAt") val updatedAt: String,
    @SerializedName("bookLists") val bookLists: List<ListTop10Data>
)

@Parcelize
data class ListTop10Data(
    @SerializedName("bookListId") val bookListId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("img") val img: String?,
    @SerializedName("bookCnt") val bookCnt: Int,
    @SerializedName("likeCnt") val likeCnt: Int,
    @SerializedName("listStatus") val listStatus: String,
    @SerializedName("likeStatus") val likeStatus: Boolean
): Parcelable