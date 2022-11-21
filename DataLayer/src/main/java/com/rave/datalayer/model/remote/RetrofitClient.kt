package com.rave.datalayer.model.remote

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.rave.datalayer.JOKE_BASE_URL
import com.rave.datalayer.QUOTE_BASE_URL
import com.rave.datalayer.TRIVIA_BASE_URL
import com.rave.datalayer.model.remote.retrofitServices.JokeRetrofitService
import com.rave.datalayer.model.remote.retrofitServices.QuoteRetrofitService
import com.rave.datalayer.model.remote.retrofitServices.TriviaService
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create

@OptIn(ExperimentalSerializationApi::class)
object RetrofitClient {
    private val interceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    private val mediaType = "application/json".toMediaType()

    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .client(client)
        .addConverterFactory(Json.asConverterFactory(mediaType))

    fun jokeServiceInstance(): JokeRetrofitService =
        retrofitBuilder
            .baseUrl(JOKE_BASE_URL)
            .build()
            .create()

    fun quoteServiceInstance(): QuoteRetrofitService =
        retrofitBuilder
            .baseUrl(QUOTE_BASE_URL)
            .build()
            .create()

    fun triviaServiceInstance(): TriviaService =
        retrofitBuilder
            .baseUrl(TRIVIA_BASE_URL)
            .build()
            .create()
}
