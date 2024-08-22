package com.bookmoa.android.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class SearchBookResponse(

    @SerializedName("title") val title: String,
    @SerializedName("item") val books: List<SearchBook>,

)
@Parcelize
data class SearchBook(

    @SerializedName("itemId") val id: Long=0,
    @SerializedName("title") val title: String="",
    @SerializedName("description") val description: String="",
    @SerializedName("author") val author: String="",
    @SerializedName("isbn13") val isbn13: String="",
    @SerializedName("publisher") val publisher: String="",
    @SerializedName("cover") val coverSmallUrl: String=""
) : Parcelable