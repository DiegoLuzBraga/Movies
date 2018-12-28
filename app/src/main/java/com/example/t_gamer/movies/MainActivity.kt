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

        callGenres()
    }

    private fun callGenres() {
        val call = RetrofitConfig().tmdbAPI().getGenres()

        call.enqueue(object : Callback, retrofit2.Callback<GenresViewModel> {
            override fun onResponse(call: Call<GenresViewModel>, response: Response<GenresViewModel>) {
                response.body()?.let {
                    val genres: GenresViewModel = it
                    setupTabs(genres.genres)
                }
                MainSRL.isRefreshing = false
            }

            override fun onFailure(call: Call<GenresViewModel>, t: Throwable) {
                if (!t.message.isNullOrEmpty()) {
                    Log.e("onFailure error", t.message)
                }
                MainSRL.isRefreshing = false
            }
        })
    }

    private fun setupTabs(genres: ArrayList<GenresDetailsViewModel>) {
        fragmentsVP.adapter = GenresTabsAdapter(supportFragmentManager, genres)
        genreTAB.setupWithViewPager(fragmentsVP)
    }
}
