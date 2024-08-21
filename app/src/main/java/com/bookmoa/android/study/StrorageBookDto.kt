package com.bookmoa.android.study

import com.google.gson.annotations.SerializedName

data class StorageBookResponse(

    @SerializedName("status") val status: String,
    @SerializedName("code") val code: String,
    @SerializedName("result") val result: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("data") val data: StorageBookInfo?

)
data class StorageBookInfo(
    @SerializedName("bookStatus") val bookStatus: String,
    @SerializedName("books") val books: List<StorageBookData>
)
data class StorageBookData(
    @SerializedName("bookId") val bookId: Long,
    @SerializedName("title") val title: String,
    @SerializedName("writer") val writer: String,
    @SerializedName("coverImage") val coverImage: String,

)