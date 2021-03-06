package com.example.t_gamer.movies.Adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.recyclerview.widget.RecyclerView
import com.example.t_gamer.movies.DB.AppDatabase
import com.example.t_gamer.movies.MovieDetailsActivity
import com.example.t_gamer.movies.Holder.MovieHolder
import com.example.t_gamer.movies.MainActivity
import com.example.t_gamer.movies.R
import com.example.t_gamer.movies.ViewModel.FavoriteMoviesViewModel
import com.example.t_gamer.movies.ViewModel.MovieViewModel
import com.squareup.picasso.Picasso

class MovieAdapter(private val movie: LiveData<PagedList<MovieViewModel>>, private val context: Context, private val tabPosition: Int? = null) :
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
        val isFavorite: Int

        if (favMovies.map { it.id }.contains(movie.value!![position]!!.id)) {
            isFavorite = 1
            holder.isFavorite?.setImageResource(R.drawable.ic_star_white_36dp)
        } else {
            isFavorite = 0
            holder.isFavorite?.setImageResource(R.drawable.ic_star_border_white_36dp)
        }

        holder.title?.text = movie.value!![position]!!.title

        Picasso.get().load("https://image.tmdb.org/t/p/w500" + (movie.value!![position]!!.poster_path))
            .into(holder.movieView)

        holder.movieCard?.setOnClickListener {
            val intent = Intent(it.context, MovieDetailsActivity::class.java)

            intent.putExtra("id", movie.value!![position]!!.id)
            intent.putExtra("title", movie.value!![position]!!.title)
            intent.putExtra("image", "https://image.tmdb.org/t/p/w500" + (movie.value!![position]!!.poster_path))
            intent.putExtra("overview", movie.value!![position]!!.overview)
            intent.putExtra("isFavorite", isFavorite)

            startActivity(it.context, intent, Bundle())
        }

        holder.isFavorite?.setOnClickListener {
            val favMovies = localData.getAllFavoriteMovies()
            if (favMovies.map { it.id }.contains(movie.value!![position]!!.id)) {
                localData.deleteFavoritedMovie(movie.value!![position]!!.id)
                holder.isFavorite?.setImageResource(R.drawable.ic_star_border_white_36dp)
            } else {
                val movieModel = movie.value!![position]!!
                localData.insertFavorite(
                    FavoriteMoviesViewModel(
                        id = movieModel.id,
                        overview = movieModel.overview,
                        poster_path = movieModel.poster_path,
                        title = movieModel.title
                    )
                )
                holder.isFavorite?.setImageResource(R.drawable.ic_star_white_36dp)
            }
            (context as MainActivity).adapter.setFavorite(tabPosition)
        }
    }

    override fun getItemCount(): Int {
        return arrayOf(movie).size
    }

}