package com.example.t_gamer.movies.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.t_gamer.movies.Adapter.MovieAdapter
import com.example.t_gamer.movies.R
import com.example.t_gamer.movies.API.RetrofitConfig
import com.example.t_gamer.movies.DB.AppDatabase
import com.example.t_gamer.movies.ViewModel.MovieResultViewModel
import com.example.t_gamer.movies.ViewModel.MovieViewModel
import com.example.t_gamer.movies.ViewModel.movie_genres
import kotlinx.android.synthetic.main.movies_per_genre_fragment.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MoviesFragment : Fragment() {

    companion object {
        fun newInstance(id: Int): MoviesFragment {
            val movieFragment = MoviesFragment()
            val args = Bundle()
            args.putInt("id", id)
            movieFragment.arguments = args
            return movieFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.movies_per_genre_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callRetrofit()
    }

    private fun callRetrofit() {
        val call = RetrofitConfig().tmdbAPI().getMoviesByGenre(arguments!!.getInt("id"))
        val dbContext = AppDatabase.instance.moviesDAO()

        call.enqueue(object : Callback, retrofit2.Callback<MovieResultViewModel> {
            override fun onFailure(call: Call<MovieResultViewModel>, t: Throwable) {
                if (!t.message.isNullOrEmpty()) {
                    Log.e("onFailure error", t.message)
                    setupRecycle(dbContext.getMoviesByGenres(listOf(arguments!!.getInt("id"))))
                }
            }

            override fun onResponse(call: Call<MovieResultViewModel>, response: Response<MovieResultViewModel>) {
                response.body()?.let {
                    val movies: MovieResultViewModel = it
                    dbContext.insertMovie(movies.results)
                    val movieGenre = movie_genres(movieId = movies.results[0].id, genreId = arguments!!.getInt("id"), id = arguments!!.getInt("id"))
                    dbContext.insertMovieGenres(movieGenre)
                    setupRecycle(movies.results)
                }
            }
        })
    }

    private fun setupRecycle(movies: List<MovieViewModel>) {
        itemsRV?.adapter = MovieAdapter(movies, context!!)
        itemsRV?.layoutManager = GridLayoutManager(activity, 2)
    }
}