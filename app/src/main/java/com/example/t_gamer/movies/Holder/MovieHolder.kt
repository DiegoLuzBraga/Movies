package com.example.t_gamer.movies.Holder
import androidx.appcompat.widget.CardView
import android.widget.TextView
import androidx.appcompat.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_movie_details.view.*
import kotlinx.android.synthetic.main.movie_card.view.*

class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var movieCard: CardView? = itemView.movieCard
    var title: TextView? = itemView.titleTXT
    var movieView: ImageView? = itemView.posterIMG
    var isFavorite: ImageView? = itemView.favoriteIMG
}