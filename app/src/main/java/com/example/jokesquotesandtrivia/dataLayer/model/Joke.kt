package com.example.jokesquotesandtrivia.dataLayer.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class Joke(
    val id:String,
    val joke:String,
    val status:Int
)