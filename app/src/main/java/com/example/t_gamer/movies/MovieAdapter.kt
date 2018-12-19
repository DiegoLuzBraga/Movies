package com.example.t_gamer.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import android.widget.ArrayAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.view.*


class MovieAdapter(movie: List<MovieViewModel>) : RecyclerView.Adapter<MovieHolder>() {

    private var movieList: List<MovieViewModel>? = movie

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.title.text = movieList?.get(position)?.title
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + (movieList?.get(position)?.poster_path))
            .into(holder.movieView)
    }

    override fun getItemCount(): Int {
        return if (movieList != null) movieList?.size!! else 0
    }
}