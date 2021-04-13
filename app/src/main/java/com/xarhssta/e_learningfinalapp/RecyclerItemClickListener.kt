package com.xarhssta.e_learningfinalapp

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemClickListener (context: Context, recyclerView: RecyclerView, private val listener: OnRecyclerClickListener)
    :RecyclerView.SimpleOnItemTouchListener(){

    interface OnRecyclerClickListener {
        fun onItemClick(view: View, position: Int)
    }

    private val gestureDetector = GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                val childview: View = recyclerView.findChildViewUnder(e.x, e.y)!!
                listener.onItemClick(childview, recyclerView.getChildAdapterPosition(childview))
                return true
            }
    })

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(e)
    }

}
