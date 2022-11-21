package com.example.jokesquotesandtrivia.uiLayer.jokeScreens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.jokesquotesandtrivia.R
import com.example.jokesquotesandtrivia.businessLayer.viewModels.JokeViewModel
import com.example.jokesquotesandtrivia.databinding.FragmentJokesBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class JokesFragment : Fragment() {

    private var _jokesBinding: FragmentJokesBinding? = null

    lateinit var theView: View
    var currentJoke: String? = null

    private val mainViewModel by viewModels<JokeViewModel>()

    override fun onAttach(context: Context) {
        mainViewModel.getRandomJoke()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _jokesBinding = FragmentJokesBinding.inflate(inflater, container, false)

        setUpObservables()

        setUpClickListeners()

        return theView
    }

    private fun setUpClickListeners() = with(_jokesBinding) {
        this?.newJoke?.setOnClickListener {
            mainViewModel.getRandomJoke()
        }
        this?.newQuote?.setOnClickListener {
            // mainViewModel.getQuote()
        }
        this?.exit?.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }
    }

    private fun setUpObservables() = lifecycleScope.launch {
        mainViewModel.jokeState.collectLatest { jokeState ->
            currentJoke = jokeState.joke
            _jokesBinding?.jokeText?.text = currentJoke
        }

        /* mainViewModel.currentQuote.observe(viewLifecycleOwner, {
             currentQuote = it
             var jokeString =
                 "Quote: ${currentQuote?.quoteText} \n\nAuthor: ${currentQuote?.quoteAuthor}"
             jokesBinding.jokeText.text = jokeString
         }
        )*/
    }

    override fun onDestroy() {
        super.onDestroy()
        _jokesBinding = null
    }
}
