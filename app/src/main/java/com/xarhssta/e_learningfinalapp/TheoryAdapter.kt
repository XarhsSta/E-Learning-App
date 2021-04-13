package com.xarhssta.e_learningfinalapp


import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class TheoryAdapter(private val context: Context, private val itemList: List<Theory>):
    RecyclerView.Adapter<TheoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.class_card, parent, false)
        return TheoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: TheoryViewHolder, position: Int) {
        val data = itemList[position]
        holder.classTitle.text = data.title
        val imageString = data.image
        val imageId = context.resources.getIdentifier(
            imageString,
            "drawable",
            context.packageName
        )
        holder.classImageView.setImageResource(imageId)
        holder.classDescription.text = data.description
    }

    override fun getItemCount(): Int {
        return if(itemList.isNotEmpty()) itemList.size else 1
    }

}

class TheoryViewHolder(view: View): RecyclerView.ViewHolder(view) {

    val classTitle: TextView = view.findViewById(R.id.classTitle)
    val classImageView: ImageView = view.findViewById(R.id.classImage)
    val classDescription: TextView = view.findViewById(R.id.classDescription)

}

data class Theory(var title: String, var image:String?, var description:String?, var videoID: String?){
    override fun toString(): String {
       return "Title: $title, ImageName: $image, Description: $description"
    }
}