package com.example.jokesquotesandtrivia.businessLayer.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokesquotesandtrivia.dataLayer.RetrofitClient
import com.example.jokesquotesandtrivia.dataLayer.model.Joke
import com.example.jokesquotesandtrivia.dataLayer.model.Quote
import com.example.jokesquotesandtrivia.dataLayer.model.TriviaQuestion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.await

class MainViewModel : ViewModel() {

    val JOSH_TAG = "JOSH"

    var currentJoke: MutableLiveData<Joke> = MutableLiveData()
    var currentQuote: MutableLiveData<Quote> = MutableLiveData()
    var questionList: MutableLiveData<List<TriviaQuestion>> = MutableLiveData()


    fun getRandomJoke() = viewModelScope.launch(Dispatchers.IO) {
        withContext(Dispatchers.Main) {
            currentJoke.value = RetrofitClient.jokeServiceInstance().getJoke()
        }
        Log.e(JOSH_TAG, "current joke value INSIDE of async: ${currentJoke.value}")
    }

    //Api for dictum is down ( returns 404)
    fun getQuote() = viewModelScope.launch(Dispatchers.IO) {
        val quoteResponse = RetrofitClient.quoteServiceInstance().getQuote()

        withContext(Dispatchers.Main) {
            if (quoteResponse.statusCode == 200)
                currentQuote.value = quoteResponse.Quotes[0]
        }
        Log.e(JOSH_TAG, "Current quote was ${currentQuote.value}")
    }

    fun getTriviaQuestions(totalQuestions: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            val questionsResponse =
                RetrofitClient.triviaServiceInstance().getQuickGame(totalQuestions)
            Log.e(JOSH_TAG, "GETTING GAME QUESTIONS")
            val response = questionsResponse.await()
            Log.e(JOSH_TAG, "getTriviaQuestions: QUESTION RESPONSE RETURNS")
            withContext(Dispatchers.Main) {
                questionList.value = response.results
            }
        }
}
