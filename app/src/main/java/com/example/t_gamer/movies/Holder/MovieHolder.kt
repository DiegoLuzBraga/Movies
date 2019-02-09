package com.example.t_gamer.movies.Holder
import android.widget.TextView
import android.view.View
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.movie_card.view.*

class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var movieCard: CardView? = itemView.movieCard
    var title: TextView? = itemView.titleTXT
    var movieView: ImageView? = itemView.posterIMG
    var isFavorite: ImageView? = itemView.favoriteIMG
}