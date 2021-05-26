package com.example.jokesquotesandtrivia.dataLayer.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class QuoteResponse(
    val statusCode: Int,
    val message: String,
    val pagination :Pagination,
    val totalQuotes:Int,
    @Json(name = "data")
    val Quotes: List<Quote>
)
