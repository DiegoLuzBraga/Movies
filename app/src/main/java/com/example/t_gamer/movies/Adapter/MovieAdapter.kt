package com.example.t_gamer.movies.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.support.v7.widget.RecyclerView
import com.example.t_gamer.movies.DB.AppDatabase
import com.example.t_gamer.movies.MovieDetailsActivity
import com.example.t_gamer.movies.Holder.MovieHolder
import com.example.t_gamer.movies.MainActivity
import com.example.t_gamer.movies.R
import com.example.t_gamer.movies.ViewModel.FavoriteMoviesViewModel
import com.example.t_gamer.movies.ViewModel.MovieViewModel
import com.squareup.picasso.Picasso

class MovieAdapter(private val movie: List<MovieViewModel>, private val context: Context) :
    RecyclerView.Adapter<MovieHolder>() {

    val localData = AppDatabase.instance.moviesDAO()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.movie_card, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val favMovies = localData.getAllFavoriteMovies()
        if (favMovies.map { it.id }.contains(movie?.get(position).id)) {
            holder.isFavorite?.setImageResource(R.drawable.ic_star_white_36dp)
        } else {
            holder.isFavorite?.setImageResource(R.drawable.ic_star_border_white_36dp)
        }

        holder.title?.text = movie?.get(position)?.title

        Picasso.get().load("https://image.tmdb.org/t/p/w500" + (movie?.get(position)?.poster_path))
            .into(holder.movieView)

        holder.movieCard?.setOnClickListener {
            val intent = Intent(it.context, MovieDetailsActivity::class.java)
            intent.putExtra("title", movie?.get(position)?.title)
            intent.putExtra("image", "https://image.tmdb.org/t/p/w500" + (movie?.get(position)?.poster_path))
            intent.putExtra("overview", movie?.get(position)?.overview)
            startActivity(it.context, intent, Bundle())
        }

        holder.isFavorite?.setOnClickListener {
            val favMovies = localData.getAllFavoriteMovies()
            if (favMovies.map { it.id }.contains(movie?.get(position).id)) {
                localData.deleteFavoritedMovie(movie?.get(position).id)
                holder.isFavorite?.setImageResource(R.drawable.ic_star_border_white_36dp)
            } else {
                val mv = movie?.get(position)
                localData.insertFavorite(
                    FavoriteMoviesViewModel(
                        id = mv.id,
                        overview = mv.overview,
                        poster_path = mv.poster_path,
                        title = mv.title
                    )
                )
                holder.isFavorite?.setImageResource(R.drawable.ic_star_white_36dp)
            }
            (context as MainActivity).adapter.setFavorite()
        }
    }

    override fun getItemCount(): Int {
        return movie.size
    }

}