package com.example.t_gamer.movies
import android.widget.ImageButton
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView

class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var title: TextView
    var movieView: ImageView
    init {
        title = itemView.findViewById(R.id.titleTXT)
        movieView = itemView.findViewById(R.id.posterIMG)
    }
}