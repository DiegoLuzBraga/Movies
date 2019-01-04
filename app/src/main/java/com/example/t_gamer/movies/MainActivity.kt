package com.example.t_gamer.movies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu?.findItem(R.id.searchBTN)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // TODO Chamada da API de pesquisa - TODO do TODO
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if ((newText.isNullOrEmpty()) && newText!!.length > 2) {
                    // TODO search movie e avaliar
                    return true
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
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
