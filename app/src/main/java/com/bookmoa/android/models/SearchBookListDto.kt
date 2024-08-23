package com.bookmoa.android.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SearchBookListResponse (
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("data") val data: List<SearchBookListData>
)


@Parcelize
data class SearchBookListData(

    @SerializedName("bookListId") val id: Int=0,
    @SerializedName("title") val title: String="",
    @SerializedName("img") val img: String="",
    @SerializedName("likeCnt") val likeCnt: String="",
    @SerializedName("bookCnt") val bookCnt: String="",
    @SerializedName("likeStatus") val likeStatus: String="",
    @SerializedName("createAt") val createAt: String="",


) : Parcelable