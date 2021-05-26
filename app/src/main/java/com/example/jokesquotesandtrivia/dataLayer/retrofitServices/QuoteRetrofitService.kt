package com.example.jokesquotesandtrivia.dataLayer.retrofitServices

import com.example.jokesquotesandtrivia.businessLayer.QUOTE_BASE_URL
import com.example.jokesquotesandtrivia.dataLayer.model.QuoteResponse
import retrofit2.http.GET

interface QuoteRetrofitService : RetrofitService {
    @GET("$QUOTE_BASE_URL/quotes/random")
    suspend fun getQuote(): QuoteResponse
}