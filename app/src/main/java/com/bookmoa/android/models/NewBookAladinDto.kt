package com.bookmoa.android.models

import com.google.gson.annotations.SerializedName


data class NewBookAladin(
    @SerializedName("title") val title: String,
    @SerializedName("writer") val writer: String,
   // @SerializedName("description") val  description: String?,
    @SerializedName("publisher") val publisher: String,
    @SerializedName("isbn") val isbn: String,
    @SerializedName("page") val page: Int,
    @SerializedName("coverImage") val coverImage: String,
)