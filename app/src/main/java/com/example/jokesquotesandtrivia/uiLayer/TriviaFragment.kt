package com.example.jokesquotesandtrivia.uiLayer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.jokesquotesandtrivia.R
import com.example.jokesquotesandtrivia.businessLayer.DEFAULT_GAME_SIZE
import com.example.jokesquotesandtrivia.businessLayer.cleanText
import com.example.jokesquotesandtrivia.businessLayer.viewModels.MainViewModel
import com.example.jokesquotesandtrivia.dataLayer.model.TriviaQuestion
import com.google.android.material.button.MaterialButton
import com.google.android.material.radiobutton.MaterialRadioButton
import com.google.android.material.textview.MaterialTextView


class TriviaFragment : Fragment() {
    private val TRUE_FALSE: String = "boolean"

    var currentGameQuestions: List<TriviaQuestion> = listOf()
    var score = 0
    var checkAnswer: Boolean = false

    var currentQuestionIndex = 0

    val mainViewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(MainViewModel::class.java)
    }

    lateinit var questionText: MaterialTextView
    lateinit var scoreText: MaterialTextView
    lateinit var submit: MaterialButton
    lateinit var exit: MaterialButton


    lateinit var answerGroup: RadioGroup
    lateinit var option1: MaterialRadioButton
    lateinit var option2: MaterialRadioButton
    lateinit var option3: MaterialRadioButton
    lateinit var option4: MaterialRadioButton
    lateinit var theView: View


    override fun onAttach(context: Context) {
        mainViewModel.getTriviaQuestions(DEFAULT_GAME_SIZE)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        theView = inflater.inflate(R.layout.fragment_trivia, container, false)

        questionText = theView.findViewById(R.id.triviaQuestion)
        scoreText = theView.findViewById(R.id.scoreText)
        submit = theView.findViewById(R.id.submit)
        exit = theView.findViewById(R.id.exit)

        option1 = theView.findViewById(R.id.option1)
        option2 = theView.findViewById(R.id.option2)
        option3 = theView.findViewById(R.id.option3)
        option4 = theView.findViewById(R.id.option4)
        answerGroup = theView.findViewById(R.id.answerGroup)

        val scoreString = getString(R.string.score, score, currentGameQuestions.size)
        scoreText.text = scoreString

        setUpObservables()

        setClickListeners()

        return theView
    }

    private fun setClickListeners() {
        exit.setOnClickListener {
            theView.findNavController().navigate(R.id.mainFragment)
        }

        submit.setOnClickListener {
            if (checkAnswer) {
                checkForCorrectAnswer()
                if (++currentQuestionIndex < currentGameQuestions.size) {
                    setQuestion(currentGameQuestions[currentQuestionIndex])
                } else {
                    displayScore()
                }
            }
        }

    }

    private fun displayScore() {
        val scoreString =
            "Your total score was $score/${currentGameQuestions.size} \n Thanks for playing!"
        questionText.text = scoreString
        answerGroup.isVisible = false
    }

    private fun setQuestion(question: TriviaQuestion) {
        val scoreString =
            "Question #${currentQuestionIndex + 1}: \n\n ${question.question.cleanText()}"
        questionText.text = scoreString
        val options: MutableList<String> = mutableListOf()

        question.incorrectAnswers.forEach {
            options.add(it.cleanText())
        }
        options.add(question.correctAnswer.cleanText())
        options.shuffle()

        when (question.type) {
            TRUE_FALSE -> {
                option1.text = options[0]
                option2.text = options[1]
                option3.isVisible = false
                option4.isVisible = false
            }
            else -> {
                option1.text = options[0]
                option2.text = options[1]
                option3.text = options[2]
                option4.text = options[3]

                option3.isVisible = true
                option4.isVisible = true

            }
        }
        checkAnswer = true
    }

    private fun checkForCorrectAnswer() {
        val selectedAnswer = theView.findViewById<RadioButton>(answerGroup.checkedRadioButtonId)
        val currentQuestion = currentGameQuestions[currentQuestionIndex]

        if (selectedAnswer.text.contentEquals(currentQuestion.correctAnswer)) {
            score++
            Toast.makeText(context, "CORRECT!!!!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Sorry, incorrect answer", Toast.LENGTH_SHORT).show()
        }

        val scoreString = getString(R.string.score, score, currentGameQuestions.size)
        scoreText.text = scoreString
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