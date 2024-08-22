package com.bookmoa.android.models

data class BookCheckResponse(
    val status: String,
    val code: String,
    val result: Boolean,
    val description: String,
    val data: BookCheckData?
)

data class BookCheckData(
    val bookId: Long
)