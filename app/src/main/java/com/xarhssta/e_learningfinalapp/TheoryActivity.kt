package com.xarhssta.e_learningfinalapp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream


class TheoryActivity : AppCompatActivity(),
        RecyclerItemClickListener.OnRecyclerClickListener
{

    private var tag: Int = 0
    private var videoFlag: Boolean = false
    lateinit var recyclerView:RecyclerView
    private lateinit var videoAdapter:VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theory)
        setSupportActionBar(findViewById(R.id.toolbar))

        Log.d("TheoryActivity", ".onCreate called")
        tag = intent.getIntExtra(getString(R.string.intentKey), 0)
        recyclerView= findViewById(R.id.recyclerView)
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            LinearLayoutManager(this).orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(this, recyclerView, this))
        recyclerView.layoutManager = LinearLayoutManager(this)
        runTheory()
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_theory, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId)
        {
            R.id.action_theory->{
                item.isChecked = true
                videoFlag = false
                runTheory()
                true
            }
            R.id.action_youtube-> {
                item.isChecked = true
                videoFlag = true
                checkForPermission()
                runVideo()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initializeTheoryList(): List<Theory> {
        val list: MutableList<Theory> = arrayListOf()
        val file: String? = when (tag) {
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
                    val theory = Theory(title, image, description, null)
                    list.add(theory)
                }
            } catch (e: Exception) {
                Log.d("TheoryActivity", "Error: ${e.message}")
            }
        }
        return list
    }


    private fun initializeVideoList(): List<Theory> {
        val list: MutableList<Theory> = arrayListOf()
        val file: String? = when (tag) {
            1 -> "add_video.json"
            2 -> "dif_video.json"
            3 -> "mul_video.json"
            4 -> "div_video.json"
            else -> null
        }
        if (file != null) {
            try {
                val jsonObject = JSONObject(loadJSONFromAsset(file))
                val jsonArray = jsonObject.getJSONArray("Class")
                for (i in 0 until jsonArray.length()) {
                    val arrayObject = jsonArray.getJSONObject(i)
                    val title = arrayObject.getString("Title")
                    val videoId = arrayObject.getString("VideoID")
                    val theory = Theory(title, null, null, videoId)
                    list.add(theory)
                }
            } catch (e: Exception) {
                Log.d("TheoryActivity","Error ${e.message}")
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

    private fun runTheory() {
        val itemList: List<Theory> = initializeTheoryList()
        val theoryAdapter = TheoryAdapter(this, itemList)
        recyclerView.adapter = theoryAdapter
    }

    private fun runVideo() {
        val itemList: List<Theory> = initializeVideoList()
        videoAdapter = VideoAdapter(this, itemList)
        recyclerView.adapter = videoAdapter
    }

    private fun checkForPermission () {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.INTERNET),1)
        }
    }

    override fun onItemClick(view: View, position: Int) {
        if(videoFlag) {
            val videoId = videoAdapter.getVideo(position)
            val intent = Intent(this, YoutubeActivity::class.java)
            intent.putExtra("videoId", videoId)
            startActivity(intent)
        }
    }
}