package com.rave.datalayer

import com.rave.datalayer.model.local.entitiy.TriviaQuestion
import com.rave.datalayer.model.remote.RetrofitClient
import com.rave.datalayer.model.remote.dto.TriviaResponse

object QuestionRepo {
    private val triviaService by lazy {
        RetrofitClient.triviaServiceInstance()
    }

    fun getQuickGame(gameSize: Int): Result<List<TriviaQuestion>> = with(triviaService) {
        val initialResponse = getQuickGame(gameSize).execute()
        if (initialResponse.isSuccessful) {
            val response = initialResponse.body() ?: TriviaResponse()
            val questions = response.results.map { current ->
                with(current) {
                    TriviaQuestion(
                        category,
                        type,
                        difficulty,
                        question,
                        correctAnswer,
                        incorrectAnswers
                    )
                }
            }
            Result.success(questions)
        } else {
            Result.failure(Exception(initialResponse.message()))
        }
    }
}
