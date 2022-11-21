package com.rave.datalayer.model.remote.retrofitServices

import com.rave.datalayer.model.remote.dto.Joke
import retrofit2.http.GET
import retrofit2.http.Headers

interface JokeRetrofitService : RetrofitService {
    @Headers(
        "Accept: application/json",
        "User-Agent: JokesQuotesAndTrivia (Practice App)"
    )
    @GET("/")
    suspend fun getJoke(): Joke
}
