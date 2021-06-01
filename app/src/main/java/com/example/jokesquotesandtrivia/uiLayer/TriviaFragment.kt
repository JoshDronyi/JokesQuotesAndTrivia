package com.example.jokesquotesandtrivia.uiLayer

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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.jokesquotesandtrivia.R
import com.example.jokesquotesandtrivia.businessLayer.DEFAULT_GAME_SIZE
import com.example.jokesquotesandtrivia.businessLayer.cleanText
import com.example.jokesquotesandtrivia.businessLayer.viewModels.MainViewModel
import com.example.jokesquotesandtrivia.dataLayer.model.TriviaQuestion
import com.example.jokesquotesandtrivia.databinding.FragmentTriviaBinding


class TriviaFragment : Fragment() {
    private val TRUE_FALSE: String = "boolean"

    var currentGameQuestions: List<TriviaQuestion> = listOf()
    var score = 0
    var checkAnswer: Boolean = false

    var currentQuestionIndex = 0
    var selectedID: Int = 0

    private var _triviaBinding: FragmentTriviaBinding? = null
    val triviaBinding = _triviaBinding!!


    val mainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }

    override fun onAttach(context: Context) {
        mainViewModel.getTriviaQuestions(DEFAULT_GAME_SIZE)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
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
            triviaBinding.root.findNavController()?.navigate(R.id.mainFragment)
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

        triviaBinding.answerGroup.setOnCheckedChangeListener { group, checkedId ->
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
            TRUE_FALSE -> {
                triviaBinding.option1.text = options[0]
                triviaBinding.option2.text = options[1]
                triviaBinding.option3.isVisible = false
                triviaBinding.option4.isVisible = false
            }
            else -> {
                triviaBinding.option1.text = options[0]
                triviaBinding.option2.text = options[1]
                triviaBinding.option3.text = options[2]
                triviaBinding.option4.text = options[3]

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
        mainViewModel.questionList.observe(viewLifecycleOwner, {
            currentGameQuestions = it
            setUpNewGame()
        })
    }

    private fun setUpNewGame() {
        currentQuestionIndex = 0
        setQuestion(currentGameQuestions[currentQuestionIndex])
    }


    companion object {
        @JvmStatic
        fun newInstance() = TriviaFragment()
    }
}