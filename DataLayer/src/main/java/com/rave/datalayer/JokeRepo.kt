package com.rave.datalayer

import com.rave.datalayer.model.remote.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object JokeRepo {
    private val jokeService by lazy {
        RetrofitClient.jokeServiceInstance()
    }

    suspend fun getJoke() = withContext(Dispatchers.IO) {
        jokeService.getJoke()
    }
}
