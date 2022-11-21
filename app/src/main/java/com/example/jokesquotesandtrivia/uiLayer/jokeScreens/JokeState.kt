package com.example.jokesquotesandtrivia.uiLayer.jokeScreens

data class JokeState(
    val isLoading: Boolean = false,
    val joke: String = "",
    val errMsg: String = ""
)
