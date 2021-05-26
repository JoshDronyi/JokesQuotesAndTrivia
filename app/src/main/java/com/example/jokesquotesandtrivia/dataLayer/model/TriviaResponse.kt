package com.example.jokesquotesandtrivia.dataLayer.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TriviaResponse(
    @Json(name = "response_code")
    val responseCode: Int,
    val results: List<TriviaQuestion>
)