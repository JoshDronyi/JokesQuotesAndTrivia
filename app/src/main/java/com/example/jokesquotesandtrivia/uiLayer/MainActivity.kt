package com.example.jokesquotesandtrivia.uiLayer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.jokesquotesandtrivia.R
import com.example.jokesquotesandtrivia.businessLayer.viewModels.QuoteViewModel

class MainActivity : AppCompatActivity() {


    val mainViewModel = ViewModelProvider
        .NewInstanceFactory().create(QuoteViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }


}