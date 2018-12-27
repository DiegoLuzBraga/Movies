package com.example.t_gamer.movies

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.movies_per_genre_fragment.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MoviesFragment : Fragment() {

    companion object {
        fun newInstance(id: Int): MoviesFragment {
            return MoviesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.movies_per_genre_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callRetrofit()
    }

    private fun callRetrofit() {
        MainSRL.isRefreshing = true

        val call = RetrofitConfig().tmdbAPI().getPopular()

        call.enqueue(object : Callback, retrofit2.Callback<MovieResultViewModel> {
            override fun onFailure(call: Call<MovieResultViewModel>, t: Throwable) {
                if (!t.message.isNullOrEmpty()) {
                    Log.e("onFailure error", t.message)
                }
                MainSRL.isRefreshing = false
            }

            override fun onResponse(call: Call<MovieResultViewModel>, response: Response<MovieResultViewModel>) {
                itemsRCLV.visibility = View.VISIBLE
                response.body()?.let {
                    val movies: MovieResultViewModel = it
                    setupRecycle(movies.results)
                }
                MainSRL.isRefreshing = false
            }
        })
    }

    private fun setupRecycle(movies: List<MovieViewModel>) {
        itemsRCLV.adapter = MovieAdapter(movies)
        itemsRCLV.layoutManager = GridLayoutManager(activity, 2)
    }
}