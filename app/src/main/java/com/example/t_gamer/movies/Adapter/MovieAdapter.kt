package com.example.t_gamer.movies.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import com.example.t_gamer.movies.MovieDetailsActivity
import com.example.t_gamer.movies.Holder.MovieHolder
import com.example.t_gamer.movies.R
import com.example.t_gamer.movies.ViewModel.MovieViewModel
import com.squareup.picasso.Picasso

class MovieAdapter(private val movie: List<MovieViewModel>, private val context: Context) :
    RecyclerView.Adapter<MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.movie_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.title?.text = movie?.get(position)?.title

        Picasso.get().load("https://image.tmdb.org/t/p/w500" + (movie?.get(position)?.poster_path))
            .into(holder.movieView)

        holder.movieCard?.setOnClickListener {
            val intent = Intent(it.context, MovieDetailsActivity::class.java)
            intent.putExtra("title", movie?.get(position)?.title)
            intent.putExtra("image", "https://image.tmdb.org/t/p/w500" + (movie?.get(position)?.poster_path))
            intent.putExtra("overview", movie?.get(position)?.overview)
            intent.putExtra("isFavorite", movie?.get(position)?.isFavorite)
            startActivity(it.context, intent, Bundle())
        }

        holder.isFavorite?.setOnClickListener {
            if(movie?.get(position)?.isFavorite == 0) {
                movie?.get(position)?.isFavorite = 1
                holder.isFavorite?.setImageResource(R.drawable.ic_star_white_36dp)
            } else {
                movie?.get(position)?.isFavorite = 0
                holder.isFavorite?.setImageResource(R.drawable.ic_star_border_white_36dp)
            }
        }

    }

    override fun getItemCount(): Int {
        return movie.size
    }

}