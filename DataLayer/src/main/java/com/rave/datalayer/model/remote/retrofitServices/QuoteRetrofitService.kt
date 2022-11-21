package com.rave.datalayer.model.remote.retrofitServices

import com.rave.datalayer.model.remote.dto.QuoteResponse
import retrofit2.http.GET

interface QuoteRetrofitService : RetrofitService {
    @GET(RANDOM_QUOTE_ENDPOINT)
    suspend fun getQuote(): QuoteResponse

    companion object {
        const val RANDOM_QUOTE_ENDPOINT = "/quotes/random"
    }
}
