package com.bookmoa.android.study

import com.google.gson.annotations.SerializedName


data class ListContentResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("data") val data: ListContentData?
)

data class ListContentData(
    @SerializedName("bookListId") val bookListId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("img") val img: String,
    @SerializedName("spec") val spec: String,
    @SerializedName("likeCnt") val likeCnt: Int,
    @SerializedName("bookCnt") val bookCnt: Int,
    @SerializedName("listStatus") val listStatus: String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("likeStatus") val likeStatus: Boolean,
    @SerializedName("book")val books: List<ListContentBook> = emptyList() // 책 목록은 현재 비어 있음
)

data class ListContentBook(
    @SerializedName("bookId") val bookListId: Int,
    @SerializedName("title") val title: String,
    @SerializedName("coverImg") val coverImg: String,
    @SerializedName("writer") val writer: String,
    @SerializedName("number") val number: Int,

)
