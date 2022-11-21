package com.rave.datalayer.model.remote.retrofitServices

import com.rave.datalayer.model.remote.dto.TriviaResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaService : RetrofitService {
    @GET(TRIVIA_ENDPOINT)
    fun getQuickGame(@Query("amount") amount: Int): Call<TriviaResponse>

    companion object {
        const val TRIVIA_ENDPOINT = "api.php"
    }
}
