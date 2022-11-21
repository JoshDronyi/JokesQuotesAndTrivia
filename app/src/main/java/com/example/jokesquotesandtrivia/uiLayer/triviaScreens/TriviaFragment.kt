package com.example.jokesquotesandtrivia.uiLayer.triviaScreens

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.jokesquotesandtrivia.R
import com.example.jokesquotesandtrivia.businessLayer.cleanText
import com.example.jokesquotesandtrivia.businessLayer.viewModels.TriviaViewModel
import com.example.jokesquotesandtrivia.databinding.FragmentTriviaBinding
import com.rave.datalayer.model.local.entitiy.TriviaQuestion
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TriviaFragment : Fragment() {
    private val booleanQuestion: String = "boolean"

    private var currentGameQuestions: List<TriviaQuestion> =
        listOf()
    private var score = 0
    private var checkAnswer: Boolean = false

    private var currentQuestionIndex = 0
    private var selectedID: Int = 0

    private var _triviaBinding: FragmentTriviaBinding? = null
    private val triviaBinding = _triviaBinding!!

    private val mainViewModel by viewModels<TriviaViewModel>()

    override fun onAttach(context: Context) {
        mainViewModel.getTriviaQuestions()
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _triviaBinding = FragmentTriviaBinding.inflate(inflater, container, false)

        val scoreString = getString(R.string.score, score, currentGameQuestions.size)
        triviaBinding.scoreText.text = scoreString

        setUpObservables()

        setClickListeners()

        return triviaBinding.root
    }

    private fun setClickListeners() {
        triviaBinding.exit.setOnClickListener {
            findNavController().navigate(R.id.mainFragment)
        }

        triviaBinding.submit.setOnClickListener {
            if (checkAnswer) {
                checkForCorrectAnswer()
                if (++currentQuestionIndex < currentGameQuestions.size) {
                    setQuestion(currentGameQuestions[currentQuestionIndex])
                } else {
                    displayScore()
                }
            }
        }

        triviaBinding.answerGroup.setOnCheckedChangeListener { _, checkedId ->
            triviaBinding.answerGroup.setBackgroundColor(Color.LTGRAY)
            selectedID = checkedId
        }
    }

    private fun displayScore() {
        val scoreString =
            "Your total score was $score/${currentGameQuestions.size} \n Thanks for playing!"
        triviaBinding.triviaQuestion.text = scoreString
        triviaBinding.answerGroup.isVisible = false
    }

    private fun setQuestion(question: TriviaQuestion) {
        val scoreString =
            "Question #${currentQuestionIndex + 1}: \n\n ${question.question.cleanText()}"
        triviaBinding.triviaQuestion.text = scoreString

        val options: MutableList<String> = mutableListOf()

        question.incorrectAnswers.forEach {
            options.add(it.cleanText())
        }
        options.add(question.correctAnswer.cleanText())
        options.shuffle()

        when (question.type) {
            booleanQuestion -> {
                triviaBinding.option1.text = options[0]
                triviaBinding.option2.text = options[1]
                triviaBinding.option3.isVisible = false
                triviaBinding.option4.isVisible = false
            }
            else -> {
                triviaBinding.option1.text = options[AnswerOptions.FIRST.value]
                triviaBinding.option2.text = options[AnswerOptions.SECOND.value]
                triviaBinding.option3.text = options[AnswerOptions.THIRD.value]
                triviaBinding.option4.text = options[AnswerOptions.FOURTH.value]

                triviaBinding.option3.isVisible = true
                triviaBinding.option4.isVisible = true
            }
        }
        checkAnswer = true
    }

    private fun checkForCorrectAnswer() {
        val selectedAnswer = triviaBinding.answerGroup[selectedID] as RadioButton
        val currentQuestion = currentGameQuestions[currentQuestionIndex]

        if (selectedAnswer.text.contentEquals(currentQuestion.correctAnswer)) {
            score++
            Toast.makeText(context, "CORRECT!!!!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(
                context,
                "selected ${selectedAnswer.text} but answer was ${currentQuestion.correctAnswer}",
                Toast.LENGTH_SHORT
            ).show()
            Toast.makeText(context, "Sorry, incorrect answer", Toast.LENGTH_SHORT).show()
        }

        val scoreString = getString(R.string.score, score, currentGameQuestions.size)
        triviaBinding.scoreText.text = scoreString
        checkAnswer = false
    }

    private fun setUpObservables() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.triviaState.collectLatest { state ->
                    currentGameQuestions = state.questionList
                    setUpNewGame()
                }
            }
        }
    }

    private fun setUpNewGame() {
        currentQuestionIndex = 0
        setQuestion(currentGameQuestions[currentQuestionIndex])
    }
}

enum class AnswerOptions(val value: Int) {
    FIRST(0), SECOND(0), THIRD(0), FOURTH(0)
}
