package com.example.jokesquotesandtrivia.businessLayer.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokesquotesandtrivia.uiLayer.jokeScreens.JokeState
import com.rave.datalayer.JokeRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class JokeViewModel : ViewModel() {
    private val tag = "JokeViewModel"
    private val _jokeState: MutableStateFlow<JokeState> = MutableStateFlow(JokeState())
    val jokeState: StateFlow<JokeState> get() = _jokeState

    fun getRandomJoke() = viewModelScope.launch {
        _jokeState.update { it.copy(isLoading = false) }
        val jokeResponse = JokeRepo.getJoke()

        _jokeState.update {
            it.copy(isLoading = false, joke = jokeResponse.joke)
        }
        Log.e(tag, "getRandomJoke: Joke was ${jokeResponse.joke}")
    }
}
