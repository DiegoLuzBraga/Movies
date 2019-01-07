package com.example.t_gamer.movies.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.t_gamer.movies.API.RetrofitConfig
import com.example.t_gamer.movies.Adapter.MovieAdapter
import com.example.t_gamer.movies.R
import com.example.t_gamer.movies.ViewModel.MovieResultViewModel
import com.example.t_gamer.movies.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SearchFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.movies_per_genre_fragment, container, false)
    }

    fun onSearchError(errorMessage: String) {
        Log.e("onSearchError: ", errorMessage)
    }

    fun onSearchStart() {

    }

    fun onSearchResult(newText: String) {
        val call = RetrofitConfig().tmdbAPI()
        call.getMovieByTitle(newText).enqueue(object : Callback, retrofit2.Callback<MovieResultViewModel> {
            override fun onFailure(call: Call<MovieResultViewModel>, t: Throwable) {
                if (!t.message.isNullOrEmpty()) {
                    onSearchError("SearchView - onFailure error: " + t.message)
                }
            }

            override fun onResponse(
                call: Call<MovieResultViewModel>,
                response: Response<MovieResultViewModel>
            ) {
                response.body()?.let {
                    val movies: MovieResultViewModel = it
                    setupRecycle(movies.results)
                }
            }

        })
    }

    private fun setupRecycle(movies: List<MovieViewModel>) {
        searchRV?.layoutManager = GridLayoutManager(activity, 2)
        searchRV?.adapter = MovieAdapter(movies, context!!)
    }

}
