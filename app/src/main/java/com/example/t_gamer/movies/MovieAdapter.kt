package com.example.t_gamer.movies

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import com.squareup.picasso.Picasso

class MovieAdapter(movie: List<MovieViewModel>) : RecyclerView.Adapter<MovieHolder>() {
    private var movieList: List<MovieViewModel>? = movie

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.title?.text = movieList?.get(position)?.title
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + (movieList?.get(position)?.poster_path))
            .into(holder.movieView)
        holder.movieCard?.setOnClickListener{
            val intent = Intent(it.context, MovieDetailsActivity::class.java)
            intent.putExtra("title", movieList?.get(position)?.title)
            intent.putExtra("image", "https://image.tmdb.org/t/p/w500" + (movieList?.get(position)?.poster_path))
            intent.putExtra("overview", movieList?.get(position)?.overview)
            startActivity(it.context, intent, Bundle())
        }
    }

    override fun getItemCount(): Int {
        return if (movieList != null) movieList?.size!! else 0
    }

}