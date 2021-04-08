package com.xarhssta.e_learningfinalapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


class TestActivity : AppCompatActivity() {

    private var classNumber: Int? = null

    lateinit var titleTextView: TextView
    lateinit var actionTextView: TextView

    lateinit var option1Button: Button
    lateinit var option2Button: Button
    lateinit var option3Button: Button
    lateinit var option4Button: Button

    private var questionsCount: Int = 0
    private var questionDifficulty: Int = 0
    private var correctAnswer: Int = 0
    private var correctCount: Int = 0

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        classNumber = intent.getIntExtra(getString(R.string.intentKey), 0)
        Toast.makeText(this, classNumber.toString(), Toast.LENGTH_LONG).show()

        titleTextView = findViewById(R.id.titleTextView)
        actionTextView = findViewById(R.id.actionTextView)

        option1Button = findViewById(R.id.option1)
        option2Button = findViewById(R.id.option2)
        option3Button = findViewById(R.id.option3)
        option4Button = findViewById(R.id.option4)

        sharedPreferences = getSharedPreferences(getString(R.string.sharedPreferencesKey), Context.MODE_PRIVATE)

        newQuestion(classNumber!!)
    }

    private fun newQuestion(number: Int) {
        when (number) {
            1 -> newAddQuestion()
            2 -> newSubQuestion()
            3 -> newMulQuestion()
            4 -> newDivQuestion()
            5 -> {
                val randomNumber = Random.nextInt(1,4)
                newQuestion(randomNumber)
            }
        }
    }

    private fun newAddQuestion() {
        val numberA: Int
        val numberB: Int
        val answers: ArrayList<Int> = ArrayList<Int>()
        var wrongAnswer: Int = 0
        var found: Boolean
        titleTextView.text = getString(R.string.addTestTitle)
        if (questionDifficulty == 0) {
            numberA = Random.nextInt(10)
            numberB = Random.nextInt(10)
        } else {
            numberA = Random.nextInt(100)
            numberB = Random.nextInt(100)
        }
        actionTextView.text = "$numberA + $numberB"
        val sum = numberA + numberB
        correctAnswer = Random.nextInt(4)
        for (i in 0..3) {
            found = true
            if (i == correctAnswer) {
                answers.add(sum)
            } else {
                while (found && wrongAnswer != sum) {
                    wrongAnswer = if (questionDifficulty == 0) {
                        Random.nextInt(20)
                    } else {
                        Random.nextInt(200)
                    }
                    found = checkForNumber(answers, wrongAnswer)

                }
                answers.add(wrongAnswer)
            }
        }
        option1Button.text = answers[0].toString()
        option2Button.text = answers[1].toString()
        option3Button.text = answers[2].toString()
        option4Button.text = answers[3].toString()
    }

    private fun newSubQuestion() {
        val numberA: Int
        var numberB: Int
        val answers: ArrayList<Int> = ArrayList<Int>()
        var wrongAnswer: Int = 0
        var found: Boolean
        titleTextView.text = getString(R.string.difTestTitle)
        if (questionDifficulty == 0) {
            numberA = Random.nextInt(10)
            numberB = Random.nextInt(10)
        } else {
            numberA = Random.nextInt(100)
            numberB = Random.nextInt(100)
        }
        while(numberA == numberB) {
            numberB = if(questionDifficulty == 0) {
                Random.nextInt(10)
            } else {
                Random.nextInt(100)
            }
        }
        val dif = if (numberA > numberB) {
            actionTextView.text = "$numberA - $numberB"
            numberA - numberB
        } else {
            actionTextView.text = "$numberB - $numberA"
            numberB - numberA
        }
        correctAnswer = Random.nextInt(4)
        for (i in 0..3) {
            found = true
            if (i == correctAnswer) {
                answers.add(dif)
            } else {
                while (found && wrongAnswer != dif) {
                    wrongAnswer = if (questionDifficulty == 0) {
                        Random.nextInt(20)
                    } else {
                        Random.nextInt(200)
                    }
                    found = checkForNumber(answers, wrongAnswer)

                }
                answers.add(wrongAnswer)
            }
        }
        option1Button.text = answers[0].toString()
        option2Button.text = answers[1].toString()
        option3Button.text = answers[2].toString()
        option4Button.text = answers[3].toString()
    }

    private fun newMulQuestion() {
        val numberA: Int
        val numberB: Int
        val answers: ArrayList<Int> = ArrayList<Int>()
        var wrongAnswer: Int
        var found: Boolean
        titleTextView.text = getString(R.string.mulTestTitle)
        if (questionDifficulty == 0) {
            numberA = Random.nextInt(10)
            numberB = Random.nextInt(10)
        } else {
            numberA = Random.nextInt(10,20)
            numberB = Random.nextInt(10,20)
        }
        actionTextView.text = "$numberA * $numberB"
        val mul = numberA * numberB
        correctAnswer = Random.nextInt(4)
        for (i in 0..3) {
            found = true
            wrongAnswer = -1
            if (i == correctAnswer) {
                answers.add(mul)
            } else {
                while (found && wrongAnswer != mul) {
                    wrongAnswer = if (questionDifficulty == 0) {
                        Random.nextInt(20)
                    } else {
                        Random.nextInt(100,400)
                    }
                    found = checkForNumber(answers, wrongAnswer)

                }
                answers.add(wrongAnswer)
            }
        }
        option1Button.text = answers[0].toString()
        option2Button.text = answers[1].toString()
        option3Button.text = answers[2].toString()
        option4Button.text = answers[3].toString()
    }

    private fun newDivQuestion() {
        val numberA: Int
        var numberB: Int
        val answers: ArrayList<Int> = ArrayList<Int>()
        var wrongAnswer: Int
        var found: Boolean
        titleTextView.text = getString(R.string.divTestTitle)
            if (questionDifficulty == 0) {
                numberA = Random.nextInt(10)
                numberB = Random.nextInt(1,10)
            } else {
                numberA = Random.nextInt(100)
                numberB = Random.nextInt(1,10)
            }

        while (numberA.rem(numberB) != 0) {
                numberB = Random.nextInt(1, 10)
        }

        actionTextView.text = "$numberA / $numberB"
        val div = numberA.div(numberB)
        correctAnswer = Random.nextInt(4)
        for (i in 0..3) {
            found = true
            wrongAnswer = -1
            if (i == correctAnswer) {
                answers.add(div)
            } else {
                while (found && wrongAnswer != div) {
                    wrongAnswer = if (questionDifficulty == 0) {
                        Random.nextInt(10)
                    } else {
                        Random.nextInt(25)
                    }
                    found = checkForNumber(answers, wrongAnswer)

                }
                answers.add(wrongAnswer)
            }
        }
        option1Button.text = answers[0].toString()
        option2Button.text = answers[1].toString()
        option3Button.text = answers[2].toString()
        option4Button.text = answers[3].toString()
    }

    private fun checkForNumber(array: ArrayList<Int>, number: Int): Boolean {
        if (array.isNotEmpty()) {
            for (i in 0 until array.size) {
                if (array[i] == number) {
                    return true
                }
            }
        }
        return false
    }

    fun checkAnswer(view: View) {
        if (correctAnswer.toString() == view.tag) {
            correctCount += 1
        }
        questionsCount += 1
        if (questionsCount == 5) {
            questionDifficulty = 1
        }
        if (questionsCount == 10) {
            when (classNumber) {
                1-> sharedPreferences.edit().putString(getString(R.string.addKey),"$correctCount/10").apply()
                2-> sharedPreferences.edit().putString(getString(R.string.difKey),"$correctCount/10").apply()
                3-> sharedPreferences.edit().putString(getString(R.string.mulKey),"$correctCount/10").apply()
                4-> sharedPreferences.edit().putString(getString(R.string.divKey),"$correctCount/10").apply()
                5-> sharedPreferences.edit().putString(getString(R.string.finalKey),"$correctCount/10").apply()
            }
            val intent = Intent(this, ScoreActivity::class.java)
            intent.putExtra(getString(R.string.testIntentKey), correctCount)
            startActivity(intent)
        } else {
            newQuestion(classNumber!!)
        }
    }

}

