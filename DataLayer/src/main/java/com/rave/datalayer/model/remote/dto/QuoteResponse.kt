package com.rave.datalayer.model.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuoteResponse(
    val statusCode: Int,
    val message: String,
    val pagination: Pagination,
    val totalQuotes: Int,
    @SerialName("data")
    val quotes: List<Quote>
)
