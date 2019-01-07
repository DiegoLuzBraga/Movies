package com.example.t_gamer.movies

import android.app.SearchManager
import android.content.Context
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.View
import com.example.t_gamer.movies.API.RetrofitConfig
import com.example.t_gamer.movies.Adapter.GenresTabsAdapter
import com.example.t_gamer.movies.Adapter.MovieAdapter
import com.example.t_gamer.movies.DB.AppDatabase
import com.example.t_gamer.movies.Fragment.SearchFragment
import com.example.t_gamer.movies.ViewModel.GenresDetailsViewModel
import com.example.t_gamer.movies.ViewModel.GenresViewModel
import com.example.t_gamer.movies.ViewModel.MovieResultViewModel
import com.example.t_gamer.movies.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.movies_per_genre_fragment.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    private val call = RetrofitConfig().tmdbAPI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        callGenres()
    }

    private fun callGenres() {
        loadingPB.visibility = View.VISIBLE
        val localData = AppDatabase.instance.moviesDAO()

        call.getGenres().enqueue(object : Callback, retrofit2.Callback<GenresViewModel> {
            override fun onResponse(call: Call<GenresViewModel>, response: Response<GenresViewModel>) {
                response.body()?.let {
                    val genres: GenresViewModel = it
                    localData.insertGenres(genres.genres)
                    setupTabs(genres.genres)
                    loadingPB.visibility = View.GONE
                }
            }

            override fun onFailure(call: Call<GenresViewModel>, t: Throwable) {
                if (!t.message.isNullOrEmpty()) {
                    Log.e("onFailure error", t.message)
                    setupTabs(localData.getGenres())
                    loadingPB.visibility = View.GONE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.searchBTN)?.actionView as SearchView
        searchView.queryHint = getString(R.string.searchMovie)
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty() && newText!!.length > 2) {

                    if (fragmentsVP.visibility == View.VISIBLE) {
                        itemsRV.visibility = View.GONE
                        fragmentsVP.visibility = View.GONE
                        genreTAB.visibility = View.GONE
                    }

                    searchLL.visibility = View.VISIBLE
                    searchRV.visibility = View.VISIBLE
                    onSearchStart()
                    onSearchResult(newText)

                    return true
                }
                else {
                    searchLL.visibility = View.GONE
                    searchRV.visibility = View.GONE
                    itemsRV.visibility = View.VISIBLE
                    fragmentsVP.visibility = View.VISIBLE
                    genreTAB.visibility = View.VISIBLE
                }
                return true
            }
        })

        return true
    }

    private fun setupTabs(genres: ArrayList<GenresDetailsViewModel>) {
        fragmentsVP.adapter = GenresTabsAdapter(supportFragmentManager, genres)
        genreTAB.setupWithViewPager(fragmentsVP)
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

    fun onSearchError(errorMessage: String) {
        Log.e("onSearchError: ", errorMessage)
    }

    fun onSearchStart(){

    }

    private fun setupRecycle(movies: List<MovieViewModel>) {
        searchRV?.layoutManager = GridLayoutManager(this, 2)
        searchRV?.adapter = MovieAdapter(movies, this)
    }
}
