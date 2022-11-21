package com.example.jokesquotesandtrivia.uiLayer.triviaScreens

import com.rave.datalayer.model.local.entitiy.TriviaQuestion

data class TriviaState(
    val isLoading: Boolean = false,
    val questionList: List<TriviaQuestion> = emptyList(),
    val errorMsg: String = ""
)
