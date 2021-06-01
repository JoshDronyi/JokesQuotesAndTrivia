package com.example.jokesquotesandtrivia.uiLayer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.jokesquotesandtrivia.R
import com.example.jokesquotesandtrivia.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mainBinding = FragmentMainBinding.inflate(inflater, container, false)

        val controller = this.findNavController()


        binding.jokeQuoteButton.setOnClickListener {
            controller.navigate(R.id.jokesFragment)
        }

        binding.triviaButton.setOnClickListener {
            controller.navigate(R.id.triviaFragment)
        }

        super.onCreateView(inflater, container, savedInstanceState)

        return mainBinding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}