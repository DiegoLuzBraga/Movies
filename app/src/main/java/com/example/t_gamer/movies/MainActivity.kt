package com.example.t_gamer.movies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movies_per_genre_fragment.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        callRetrofit()
        MainSRL.setOnRefreshListener { callRetrofit() }
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

    private fun setupTabs(genres: List<GenresViewModel>){

    }

    private fun setupRecycle(movies: List<MovieViewModel>) {
        itemsRCLV.adapter = MovieAdapter(movies)
        itemsRCLV.layoutManager = GridLayoutManager(this, 2)
    }
}
