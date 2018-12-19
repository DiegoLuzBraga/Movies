package com.example.t_gamer.movies
import android.widget.TextView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.movie_card.view.*

class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var title: TextView = itemView.titleTXT
    var movieView: ImageView = itemView.posterIMG

}