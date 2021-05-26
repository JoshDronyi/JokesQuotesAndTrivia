package com.example.jokesquotesandtrivia.dataLayer.retrofitServices

import com.example.jokesquotesandtrivia.dataLayer.model.TriviaResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaService:RetrofitService {
    @GET("api.php")
    fun getQuickGame(@Query("amount") amount:Int) : Call<TriviaResponse>
}