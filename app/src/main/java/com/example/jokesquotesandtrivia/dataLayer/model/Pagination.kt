package com.example.jokesquotesandtrivia.dataLayer.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pagination(
    val currentPage:Int,
    val nextPage: Int?,
    val totalPages:Int
)
