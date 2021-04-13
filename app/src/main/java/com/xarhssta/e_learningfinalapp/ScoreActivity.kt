package com.xarhssta.e_learningfinalapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val correctAnswers: Int= intent.getIntExtra(getString(R.string.testIntentKey), -1)

        val scoreTextView: TextView = findViewById(R.id.scoreTextView)
        val resultTextView: TextView = findViewById(R.id.resultTextView)

        if (correctAnswers != -1) {
            scoreTextView.text = "$correctAnswers/10"
            resultTextView.text = when (correctAnswers) {
                in 0..3 -> getString(R.string.badResult)
                in 4..6 -> getString(R.string.mediumResult)
                else -> getString(R.string.goodResult)
            }
        }else {
                scoreTextView.text = getString(R.string.error)
            }
            resultTextView.visibility = View.VISIBLE
        }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }
}
