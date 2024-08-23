package com.bookmoa.android.models

import com.google.gson.annotations.SerializedName

data class BookMemoResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: String,
    @SerializedName("data") val data: BookMemoListPreviewDTO
)

data class BookMemoListPreviewDTO(
    @SerializedName("memberBookPreviewDTOList") val memberBookPreviewDTOList: MutableList<BookMemoDTO>
)

data class BookMemoDTO(
    @SerializedName("title") val title: String,
    @SerializedName("writer") val writer: String,
    @SerializedName("memberBookId") val memberBookId: Int,
    @SerializedName("memberBookStatus") val memberBookStatus: String,
    @SerializedName("readPage") val readPage: Int,
    @SerializedName("startedAt") val startedAt: String,
    @SerializedName("endedAt") val endedAt: String,
    @SerializedName("score") val score: Int,
    @SerializedName("memberId") val memberId: Int,
    @SerializedName("bookId") val bookId: Int,
    @SerializedName("image") val image: String,
)

data class BookMemoDeleteResponse (
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: String,
    @SerializedName("data") val data: String
)