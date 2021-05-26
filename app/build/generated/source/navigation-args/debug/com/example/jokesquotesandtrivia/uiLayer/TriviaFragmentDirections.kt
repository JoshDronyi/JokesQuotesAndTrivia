package com.example.jokesquotesandtrivia.uiLayer

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.jokesquotesandtrivia.R

public class TriviaFragmentDirections private constructor() {
  public companion object {
    public fun actionTriviaFragmentToMainFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_triviaFragment_to_mainFragment)
  }
}
