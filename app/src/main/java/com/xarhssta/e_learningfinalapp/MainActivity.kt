package com.xarhssta.e_learningfinalapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    public var firstTagFlag: Boolean = false

    lateinit var theoryButton: Button
    lateinit var testButton: Button
    lateinit var finalTestButton: Button

    lateinit var addButton: Button
    lateinit var subButton: Button
    lateinit var mulButton: Button
    lateinit var divButton: Button

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

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
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
            }
            startActivity(testIntent)
        }
    }
}