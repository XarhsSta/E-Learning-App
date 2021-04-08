package com.xarhssta.e_learningfinalapp

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.util.*


class TheoryActivity : AppCompatActivity() {

    private var tag: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theory)
        setSupportActionBar(findViewById(R.id.toolbar))
        Log.d("TheoryActivity", ".onCreate called")
        tag = intent.getIntExtra(getString(R.string.intentKey), 0)
        val itemList: List<Theory> = initializeList()
        val recyclerView: RecyclerView = findViewById(R.id.theoryRecyclerView)
        val theoryAdapter = TheoryAdapter(this, tag, itemList)
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            LinearLayoutManager(this).orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = theoryAdapter

        Log.d("TheoryActivity", ".onCreate ended")
    }

    private fun initializeList(): List<Theory> {
        val list: MutableList<Theory> = arrayListOf()
        val file = when (tag) {
            1 -> "add.json"
            2 -> "dif.json"
            3 -> "mul.json"
            4 -> "div.json"
            else -> null
        }
        if (file != null) {
            try {
                val jsonObject = JSONObject(loadJSONFromAsset(file))
                val jsonArray = jsonObject.getJSONArray("Class")
                for (i in 0 until jsonArray.length()) {
                    val arrayObject = jsonArray.getJSONObject(i)
                    val title = arrayObject.getString("Title")
                    val image = arrayObject.getString("Image")
                    val description = arrayObject.getString("Description")
                    val theory = Theory(title, image, description)
                    list.add(theory)
                }
            } catch (e: Exception) {
                Log.d("TheoryActivity", "Error: ${e.message}")
            }
        }
        return list
    }

    private fun loadJSONFromAsset(filename: String): String? {
        return try {
            val inputStream: InputStream = this.assets.open(filename)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }
}