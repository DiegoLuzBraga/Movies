package com.example.t_gamer.movies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val call = RetrofitConfig().tmdbAPI().getPopular()

        call.enqueue(object : Callback, retrofit2.Callback<MovieResultViewModel> {
            override fun onFailure(call: Call<MovieResultViewModel>, t: Throwable) {
                if (!t.message.isNullOrEmpty()) Log.e("onFailure error", t.message)
            }

            override fun onResponse(call: Call<MovieResultViewModel>, response: Response<MovieResultViewModel>) {
                response?.body()?.let {
                    val movies: MovieResultViewModel = it
                    setupRecycle(movies.results)
                }
            }
        })
    }

    private fun setupRecycle(movies: List<MovieViewModel>) {
        itemsRCLV.adapter = MovieAdapter(movies)
        itemsRCLV.layoutManager = GridLayoutManager(this, 2)
    }
}
