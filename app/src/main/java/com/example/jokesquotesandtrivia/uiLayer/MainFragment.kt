package com.example.jokesquotesandtrivia.uiLayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.jokesquotesandtrivia.R
import com.google.android.material.button.MaterialButton

class MainFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val theView = inflater.inflate(R.layout.fragment_main, container, false)

        val jokeButton = theView?.findViewById<MaterialButton>(R.id.jokeQuoteButton)
        val triviaButton = theView?.findViewById<MaterialButton>(R.id.triviaButton)
        val controller = this.findNavController()


        jokeButton?.setOnClickListener {
            controller.navigate(R.id.jokesFragment)
        }

        triviaButton?.setOnClickListener {
            controller.navigate(R.id.triviaFragment)
        }

        super.onCreateView(inflater, container, savedInstanceState)

        return theView
    }


}