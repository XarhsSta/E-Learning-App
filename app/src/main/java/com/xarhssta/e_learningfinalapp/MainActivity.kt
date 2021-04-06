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
import android.widget.Toast

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

        theoryButton = findViewById(R.id.theoryButton)
        testButton = findViewById(R.id.testButton)
        finalTestButton = findViewById(R.id.finalTestButton)

        addButton = findViewById(R.id.addButton)
        subButton = findViewById(R.id.subButton)
        mulButton = findViewById(R.id.mulButton)
        divButton = findViewById(R.id.divButton)

        sharedPreferences = getSharedPreferences("scores", Context.MODE_PRIVATE)

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

            Toast.makeText(
                this,
                "TAG: " + view.tag + ", and boolean is:" + firstTagFlag.toString(),
                Toast.LENGTH_LONG
            ).show()
        } else {
            val testIntent = Intent(this, TestActivity::class.java)
            when (view.tag) {
                "1" -> testIntent.putExtra("class",1)
                "2" -> testIntent.putExtra("class",2)
                "3" -> testIntent.putExtra("class",3)
                "4" -> testIntent.putExtra("class",4)
                "5" -> testIntent.putExtra("class",5)
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

        historyAddScore.text = sharedPreferences.getString("add","Δεν υπάρχει βαθμός")
        historyDifScore.text = sharedPreferences.getString("dif","Δεν υπάρχει βαθμός")
        historyMulScore.text = sharedPreferences.getString("mul","Δεν υπάρχει βαθμός")
        historyDivScore.text = sharedPreferences.getString("div","Δεν υπάρχει βαθμός")
        historyFinalScore.text = sharedPreferences.getString("final","Δεν υπάρχει βαθμός")

        val builder = AlertDialog.Builder(this)
        builder.setPositiveButton("Επαναφορά",
        DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->

            sharedPreferences.edit().apply{
                putString("add","Δεν υπάρχει βαθμός")
                putString("dif","Δεν υπάρχει βαθμός")
                putString("mul","Δεν υπάρχει βαθμός")
                putString("div","Δεν υπάρχει βαθμός")
                putString("final","Δεν υπάρχει βαθμός")
            }.apply()

        }
        )
        builder.setNegativeButton("Κλείσιμο",
           DialogInterface.OnClickListener(){ dialogInterface: DialogInterface, i: Int ->
                closeContextMenu()
           })
        val aboutDialog: AlertDialog = builder.setView(messageView).create()
        aboutDialog.setCanceledOnTouchOutside(true)
        aboutDialog.show()
    }
}