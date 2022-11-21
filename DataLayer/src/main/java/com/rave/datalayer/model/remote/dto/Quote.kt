package com.rave.datalayer.model.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Quote(
    @SerialName("_id")
    val id: String,
    val quoteText: String,
    val quoteAuthor: String,
    @SerialName("__v")
    val v: Int
)
