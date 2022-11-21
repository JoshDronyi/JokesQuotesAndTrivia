package com.example.jokesquotesandtrivia.dataLayer

import com.example.jokesquotesandtrivia.businessLayer.TRIVIA_BASE_URL
import com.example.jokesquotesandtrivia.dataLayer.model.TriviaQuestion
import com.example.jokesquotesandtrivia.dataLayer.retrofitServices.JokeRetrofitService
import com.example.jokesquotesandtrivia.dataLayer.retrofitServices.QuoteRetrofitService
import com.example.jokesquotesandtrivia.dataLayer.retrofitServices.TriviaService
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.ParameterizedType

object RetrofitClient {

    private val moshi = Moshi.Builder()
        .addLast(KotlinJsonAdapterFactory())
        .build()

    private val interceptor: HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(TRIVIA_BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()


    private val triviaListType: ParameterizedType =
        Types.newParameterizedType(List::class.java, TriviaQuestion::class.java)

    val triviaAdapter: JsonAdapter<List<TriviaQuestion>> = moshi.adapter(triviaListType)

    fun jokeServiceInstance(): JokeRetrofitService =
        retrofit.create(JokeRetrofitService::class.java)

    fun quoteServiceInstance(): QuoteRetrofitService =
        retrofit.create(QuoteRetrofitService::class.java)


    fun triviaServiceInstance(): TriviaService = retrofit.create(TriviaService::class.java)
}










