package com.example.jokesquotesandtrivia.uiLayer

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.jokesquotesandtrivia.R

public class MainFragmentDirections private constructor() {
  public companion object {
    public fun actionMainFragmentToTriviaFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_mainFragment_to_triviaFragment)

    public fun actionMainFragmentToJokesFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_mainFragment_to_jokesFragment)
  }
}
