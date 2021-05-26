package com.example.jokesquotesandtrivia.dataLayer.retrofitServices.adapters

import com.example.jokesquotesandtrivia.dataLayer.RetrofitClient
import com.example.jokesquotesandtrivia.dataLayer.model.TriviaQuestion
import com.example.jokesquotesandtrivia.dataLayer.model.TriviaResponse
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonReader
import com.squareup.moshi.ToJson
import org.json.JSONObject

class TriviaResponseAdapter() {
    val RESPONSE_CODE = "response_code"
    val QUESTION_LIST = "results"

    @ToJson
    fun toJson(triviaResponse: TriviaResponse): JSONObject {
        val triviaResponse_json = JSONObject()
        triviaResponse_json.put(RESPONSE_CODE, triviaResponse.responseCode)
        triviaResponse_json.put(QUESTION_LIST, RetrofitClient.triviaAdapter.toJson(triviaResponse.results))
        return triviaResponse_json
    }

    @FromJson
    fun fromJson(reader: JsonReader): TriviaResponse? {
        var responseCode = 0
        var questionList: List<TriviaQuestion>? = listOf()
        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()
            when (name) {
                RESPONSE_CODE -> {
                    responseCode = reader.nextInt()
                }
                QUESTION_LIST -> {
                    questionList = RetrofitClient.triviaAdapter.fromJson(reader.peekJson())

                }
                else -> throw Exception("Json Object name of '$name' is not expected for a Trivia Response.")
            }
        }
        reader.endArray()
        return questionList?.let { TriviaResponse(responseCode, it) }
    }
}