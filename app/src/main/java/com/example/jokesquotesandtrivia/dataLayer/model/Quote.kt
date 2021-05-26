package com.example.jokesquotesandtrivia.dataLayer.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Quote(
    @Json(name = "_id")
    val id: String,
    val quoteText: String,
    val quoteAuthor: String,
    @Json(name = "__v")
    val v:Int
)