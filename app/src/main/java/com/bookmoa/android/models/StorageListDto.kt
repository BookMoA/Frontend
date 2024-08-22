package com.bookmoa.android.models

import com.google.gson.annotations.SerializedName

data class StorageListResponse(
    @SerializedName("img") val img: String,
    @SerializedName("title") val title: String,
    @SerializedName("num") val num: Int,
    @SerializedName("data") val data: List<StorageListData>?
)


data class StorageListData(
    @SerializedName("bookListId") val bookListId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("img") val img: String?,
    @SerializedName("likeCnt") val likeCnt: Int,
    @SerializedName("bookCnt") val bookCnt: Int,
    @SerializedName("listStatus") val listStatus: String,
    @SerializedName("likeStatus") val likeStatus: Boolean,
    @SerializedName("storedStatus") val storedStatus: Boolean = false
)

