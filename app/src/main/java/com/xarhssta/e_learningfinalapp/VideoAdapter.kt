package com.xarhssta.e_learningfinalapp

import android.content.Context
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.youtube.player.*
import java.util.*


class VideoAdapter(private val context: Context, private val itemList: List<Theory>) :
    RecyclerView.Adapter<VideoViewHolder>(), YouTubeThumbnailView.OnInitializedListener {

    lateinit var videoId:String

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.video_card_view, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val data = itemList[position]
        videoId= data.videoID!!
        holder.videoThumbnailImageView.initialize(context.getString(R.string.API_Key), this)
        holder.videoTitle.text = data.title
    }

    override fun onInitializationSuccess(p0: YouTubeThumbnailView?, p1: YouTubeThumbnailLoader?) {
        p1?.setVideo(videoId)
    }

    override fun onInitializationFailure(
        p0: YouTubeThumbnailView?,
        p1: YouTubeInitializationResult?
    ) {
        Log.e("VideoAdapter", "Youtube Initialization Failure");
    }

    override fun getItemCount(): Int {
        return if (itemList.isNotEmpty()) itemList.size else 1
    }

    fun getVideo(position: Int): String? {
        return if (itemList.isNotEmpty()) itemList[position].videoID else null
    }

}

class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val videoTitle: TextView = view.findViewById(R.id.videoTitleTextView)
    var videoThumbnailImageView: YouTubeThumbnailView =
        view.findViewById(R.id.videoThumbnailImageView)
}