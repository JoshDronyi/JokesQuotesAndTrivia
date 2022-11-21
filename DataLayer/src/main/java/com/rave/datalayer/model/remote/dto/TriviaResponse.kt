package com.rave.datalayer.model.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TriviaResponse(
    @SerialName("response_code")
    val responseCode: Int = 0,
    val results: List<TriviaQuestion> = emptyList()
)
