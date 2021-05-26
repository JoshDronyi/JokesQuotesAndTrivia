package com.example.jokesquotesandtrivia.uiLayer

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.example.jokesquotesandtrivia.R

public class JokesFragmentDirections private constructor() {
  public companion object {
    public fun actionJokesFragmentToMainFragment(): NavDirections =
        ActionOnlyNavDirections(R.id.action_jokesFragment_to_mainFragment)
  }
}
