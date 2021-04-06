package com.xarhssta.e_learningfinalapp

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

        val correctAnswers: Int= intent.getIntExtra("correct", -1)

        val scoreTextView: TextView = findViewById(R.id.scoreTextView)
        val resultTextView: TextView = findViewById(R.id.resultTextView)

        if (correctAnswers != -1) {
            scoreTextView.text = "$correctAnswers/10"
            resultTextView.text = when(correctAnswers) {
                in 0..3 -> "Δεν έχεις διαβάσει καθόλου. Διάβασε ξανά τη θεωρία"
                in 4..6 -> "Έκανες καλή προσπάθεια. Διάβασε άλλη μια φορά τη θεωρία και πιστεύω θα τα καταφέρεις"
                else -> "Μπράβο σου. Πολύ καλή προσπάθεια"
            }
            resultTextView.visibility = View.VISIBLE
        }
    }
}