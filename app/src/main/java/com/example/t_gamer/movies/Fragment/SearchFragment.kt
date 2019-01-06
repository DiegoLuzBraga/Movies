package com.example.t_gamer.movies.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.t_gamer.movies.Adapter.MovieAdapter
import com.example.t_gamer.movies.R
import com.example.t_gamer.movies.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*

class SearchFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.movies_per_genre_fragment, container, false)
    }

    fun onSearchError(erroMessage: String) {
        Log.e("onSearchError: ", erroMessage)
    }

    fun onSearchStart() {

    }

    fun onSearchResult(movies: List<MovieViewModel>) {
        setupRecycle(movies)
    }

    private fun setupRecycle(movies: List<MovieViewModel>) {
        searchRV?.adapter = MovieAdapter(movies)
        searchRV?.layoutManager = GridLayoutManager(activity, 2)
    }

}