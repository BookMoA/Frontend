package com.bookmoa.android.models

data class BookEntryResponse(
    val status: String,
    val code: String,
    val result: Boolean,
    val description: String,
    val data: BookEntryData?
)

data class BookEntryData(
    val bookId: Long
)