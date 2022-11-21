package com.example.jokesquotesandtrivia.businessLayer.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jokesquotesandtrivia.businessLayer.STATUSCODE_OK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class QuoteViewModel : ViewModel() {

    val JOSH_TAG = "JOSH"

    var currentQuote: MutableLiveData<com.rave.datalayer.model.remote.dto.Quote> = MutableLiveData()

    // Api for dictum is down ( returns 404)
    fun getQuote() = viewModelScope.launch(Dispatchers.IO) {
        val quoteResponse =
            com.rave.datalayer.model.remote.RetrofitClient.quoteServiceInstance().getQuote()

        withContext(Dispatchers.Main) {
            if (quoteResponse.statusCode == STATUSCODE_OK) {
                currentQuote.value = quoteResponse.quotes[0]
            }
        }
        Log.e(JOSH_TAG, "Current quote was ${currentQuote.value}")
    }
}
