package com.example.jokesquotesandtrivia.dataLayer.retrofitServices

import com.example.jokesquotesandtrivia.businessLayer.JOKE_BASE_URL
import com.example.jokesquotesandtrivia.dataLayer.model.Joke
import retrofit2.http.GET
import retrofit2.http.Headers

interface JokeRetrofitService : RetrofitService {
    @Headers(
        "Accept: application/json",
        "User-Agent: JokesQuotesAndTrivia (Practice App)"
    )
    @GET(JOKE_BASE_URL)
    suspend fun getJoke(): Joke
}