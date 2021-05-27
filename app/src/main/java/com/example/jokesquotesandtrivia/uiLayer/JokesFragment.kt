package com.example.jokesquotesandtrivia.uiLayer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.jokesquotesandtrivia.R
import com.example.jokesquotesandtrivia.businessLayer.viewModels.MainViewModel
import com.example.jokesquotesandtrivia.dataLayer.model.Joke
import com.example.jokesquotesandtrivia.dataLayer.model.Quote
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class JokesFragment : Fragment() {

    lateinit var theView: View
    var currentJoke: Joke? = null
    var currentQuote: Quote? = null

    lateinit var jokeText:MaterialTextView
    lateinit var newJoke:MaterialButton
    lateinit var newQuote: MaterialButton
    lateinit var exitButton:MaterialButton

    val mainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel.getRandomJoke()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        theView = inflater.inflate(R.layout.fragment_jokes, container, false)

        jokeText = theView.findViewById(R.id.jokeText)
        newJoke = theView.findViewById(R.id.newJoke)
        newQuote = theView.findViewById(R.id.newQuote)
        exitButton = theView.findViewById(R.id.exit)

        setUpObservables()

        setUpClickListeners()

        return theView
    }

    private fun setUpClickListeners() {
        newJoke.setOnClickListener {
            mainViewModel.getRandomJoke()
        }

        newQuote.setOnClickListener {
            mainViewModel.getQuote()
        }
        exitButton.setOnClickListener {
            this.findNavController().navigate(R.id.mainFragment)
        }
    }

    private fun setUpObservables() {
        mainViewModel.currentJoke.observe(viewLifecycleOwner,{
            currentJoke = it
            jokeText.text = currentJoke?.joke
        })
        mainViewModel.currentQuote.observe(viewLifecycleOwner,{
            currentQuote = it
            jokeText.text = "Quote: ${currentQuote?.quoteText} \n\nAuthor: ${currentQuote?.quoteAuthor}"
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = JokesFragment()
    }
}