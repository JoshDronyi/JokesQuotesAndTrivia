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
import com.example.jokesquotesandtrivia.databinding.FragmentJokesBinding

class JokesFragment : Fragment() {

    private var _jokesBinding: FragmentJokesBinding? = null
    val jokesBinding get() = _jokesBinding!!

    lateinit var theView: View
    var currentJoke: Joke? = null
    var currentQuote: Quote? = null


    private val mainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        mainViewModel.getRandomJoke()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _jokesBinding = FragmentJokesBinding.inflate(inflater, container, false)

        setUpObservables()

        setUpClickListeners()

        return theView
    }

    private fun setUpClickListeners() {
        jokesBinding.newJoke.setOnClickListener {
            mainViewModel.getRandomJoke()
        }

        jokesBinding.newQuote.setOnClickListener {
            mainViewModel.getQuote()
        }
        jokesBinding.exit.setOnClickListener {
            this.findNavController().navigate(R.id.mainFragment)
        }
    }

    private fun setUpObservables() {
        mainViewModel.currentJoke.observe(viewLifecycleOwner, {
            currentJoke = it
            jokesBinding.jokeText.text = currentJoke?.joke
        })
        mainViewModel.currentQuote.observe(viewLifecycleOwner, {
            currentQuote = it
            var jokeString =
                "Quote: ${currentQuote?.quoteText} \n\nAuthor: ${currentQuote?.quoteAuthor}"
            jokesBinding.jokeText.text = jokeString

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _jokesBinding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = JokesFragment()
    }
}