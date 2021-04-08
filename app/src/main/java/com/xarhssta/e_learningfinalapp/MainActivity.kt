package com.xarhssta.e_learningfinalapp

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var firstTagFlag: Boolean = false

    lateinit var theoryButton: Button
    lateinit var testButton: Button
    lateinit var finalTestButton: Button

    lateinit var addButton: Button
    lateinit var subButton: Button
    lateinit var mulButton: Button
    lateinit var divButton: Button

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        Log.d("MainActivity",".onCreate called")
        theoryButton = findViewById(R.id.theoryButton)
        testButton = findViewById(R.id.testButton)
        finalTestButton = findViewById(R.id.finalTestButton)

        addButton = findViewById(R.id.addButton)
        subButton = findViewById(R.id.subButton)
        mulButton = findViewById(R.id.mulButton)
        divButton = findViewById(R.id.divButton)

        sharedPreferences = getSharedPreferences(getString(R.string.sharedPreferencesKey), Context.MODE_PRIVATE)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_history -> {
                showHistoryAlertDialog()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showClasses(view: View) {

        firstTagFlag = view.tag == "1"

        if (firstTagFlag) {
            finalTestButton.isClickable= false
            finalTestButton.visibility = View.INVISIBLE
        }

        theoryButton.visibility = View.INVISIBLE
        theoryButton.isClickable = false

        testButton.visibility = View.INVISIBLE
        testButton.isClickable = false

        addButton.visibility = View.VISIBLE
        addButton.isClickable = true

        subButton.visibility = View.VISIBLE
        subButton.isClickable = true

        mulButton.visibility = View.VISIBLE
        mulButton.isClickable = true

        divButton.visibility = View.VISIBLE
        divButton.isClickable = true
    }

    fun startClass(view: View) {
        if (firstTagFlag) {
            val theoryIntent = Intent(this, TheoryActivity::class.java)
            when (view.tag) {
                "1" -> theoryIntent.putExtra(getString(R.string.intentKey), 1)
                "2" -> theoryIntent.putExtra(getString(R.string.intentKey), 2)
                "3" -> theoryIntent.putExtra(getString(R.string.intentKey), 3)
                "4" -> theoryIntent.putExtra(getString(R.string.intentKey), 4)
            }
            startActivity(theoryIntent)
        } else {
            val testIntent = Intent(this, TestActivity::class.java)
            when (view.tag) {
                "1" -> testIntent.putExtra(getString(R.string.intentKey),1)
                "2" -> testIntent.putExtra(getString(R.string.intentKey),2)
                "3" -> testIntent.putExtra(getString(R.string.intentKey),3)
                "4" -> testIntent.putExtra(getString(R.string.intentKey),4)
                "5" -> testIntent.putExtra(getString(R.string.intentKey),5)
            }
            startActivity(testIntent)
        }
    }

    private fun showHistoryAlertDialog() {
        val messageView = layoutInflater.inflate(R.layout.history_layout, null, false)

        val historyAddScore: TextView = messageView.findViewById(R.id.historyAddScoreTextView)
        val historyDifScore: TextView = messageView.findViewById(R.id.historyDifScoreTextView)
        val historyMulScore: TextView = messageView.findViewById(R.id.historyMulScoreTextView)
        val historyDivScore: TextView = messageView.findViewById(R.id.historyDivScoreTextView)
        val historyFinalScore: TextView = messageView.findViewById(R.id.historyFinalScoreTextView)

        historyAddScore.text = sharedPreferences.getString(getString(R.string.addKey),getString(R.string.noGrade))
        historyDifScore.text = sharedPreferences.getString(getString(R.string.difKey),getString(R.string.noGrade))
        historyMulScore.text = sharedPreferences.getString(getString(R.string.mulKey),getString(R.string.noGrade))
        historyDivScore.text = sharedPreferences.getString(getString(R.string.divKey),getString(R.string.noGrade))
        historyFinalScore.text = sharedPreferences.getString(getString(R.string.finalKey),getString(R.string.noGrade))

        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton(getString(R.string.reset),
        DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->

            sharedPreferences.edit().apply{
                putString(getString(R.string.addKey),getString(R.string.noGrade))
                putString(getString(R.string.difKey),getString(R.string.noGrade))
                putString(getString(R.string.mulKey),getString(R.string.noGrade))
                putString(getString(R.string.divKey),getString(R.string.noGrade))
                putString(getString(R.string.finalKey),getString(R.string.noGrade))
            }.apply()

        }
        )
        builder.setNegativeButton(getString(R.string.close),
           DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
                closeContextMenu()
           })
        val aboutDialog: AlertDialog = builder.setView(messageView).create()
        aboutDialog.setCanceledOnTouchOutside(true)
        aboutDialog.show()
    }

    private fun resetButtons() {

        firstTagFlag = false
        
        finalTestButton.isClickable = true
        finalTestButton.visibility = View.VISIBLE

        theoryButton.visibility = View.VISIBLE
        theoryButton.isClickable = true

        testButton.visibility = View.VISIBLE
        testButton.isClickable = true

        addButton.visibility = View.INVISIBLE
        addButton.isClickable = false

        subButton.visibility = View.INVISIBLE
        subButton.isClickable = false

        mulButton.visibility = View.INVISIBLE
        mulButton.isClickable = false

        divButton.visibility = View.INVISIBLE
        divButton.isClickable = false
    }

    override fun onBackPressed() {
        if (!theoryButton.isClickable) {
            resetButtons()
        } else {
            super.onBackPressed()
        }
    }
}