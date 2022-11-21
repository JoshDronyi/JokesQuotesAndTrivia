package com.example.jokesquotesandtrivia.businessLayer.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokesquotesandtrivia.businessLayer.DEFAULT_GAME_SIZE
import com.example.jokesquotesandtrivia.uiLayer.triviaScreens.TriviaState
import com.rave.datalayer.QuestionRepo
import com.rave.datalayer.model.local.entitiy.TriviaQuestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TriviaViewModel : ViewModel() {
    private val tag = "TriviaViewModel"
    private val _triviaState: MutableStateFlow<TriviaState> = MutableStateFlow(TriviaState())
    val triviaState: StateFlow<TriviaState> get() = _triviaState

    fun getTriviaQuestions(totalQuestions: Int = DEFAULT_GAME_SIZE) =
        viewModelScope.launch(Dispatchers.IO) {
            _triviaState.update {
                it.copy(isLoading = true)
            }

            val questionsResponse: Result<List<TriviaQuestion>> =
                QuestionRepo.getQuickGame(totalQuestions)

            Log.e(tag, "GETTING GAME QUESTIONS")

            if (questionsResponse.isSuccess) {
                _triviaState.update {
                    it.copy(
                        isLoading = false,
                        questionList = questionsResponse.getOrDefault(emptyList())
                    )
                }
                Log.e(tag, "getTriviaQuestions: QUESTION RESPONSE RETURNS")
            } else {
                Log.e(
                    tag,
                    "getTriviaQuestions: WE HAD AN ERROR GETTING questions. $questionsResponse"
                )
            }
        }
}
