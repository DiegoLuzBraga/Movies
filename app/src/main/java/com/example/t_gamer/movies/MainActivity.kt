package com.example.t_gamer.movies

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.example.t_gamer.movies.API.RetrofitConfig
import com.example.t_gamer.movies.Adapter.GenresTabsAdapter
import com.example.t_gamer.movies.Adapter.MovieAdapter
import com.example.t_gamer.movies.DB.AppDatabase
import com.example.t_gamer.movies.ViewModel.GenresDetailsViewModel
import com.example.t_gamer.movies.ViewModel.GenresViewModel
import com.example.t_gamer.movies.ViewModel.MovieResultViewModel
import com.example.t_gamer.movies.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.movie_card.*
import kotlinx.android.synthetic.main.movies_per_genre_fragment.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    private val call = RetrofitConfig().tmdbAPI()

    internal lateinit var adapter: GenresTabsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(moviesTB)

        supportActionBar?.setDisplayShowTitleEnabled(false)
        appNameTV.text = getString(R.string.app_name)

        callGenres()
        mainReloadBTN.setOnClickListener {
            reloadGenres()
        }
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
                    onSearchError(getString(R.string.callGenresError))
                    mainReloadBTN.visibility = View.VISIBLE
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
                mainErrorLL.visibility = View.GONE
                if (!newText.isNullOrEmpty() && newText!!.length > 2) {

                    if (moviesVP.visibility == View.VISIBLE) {
                        itemsRV.visibility = View.GONE
                        moviesVP.visibility = View.GONE
                        genreTAB.visibility = View.GONE
                        mainErrorLL.visibility = View.GONE
                    }

                    searchLL.visibility = View.VISIBLE
                    searchRV.visibility = View.VISIBLE

                    onSearchStart()

                    onSearchResult(newText)

                    return true
                } else {
                    searchLL.visibility = View.GONE
                    searchRV.visibility = View.GONE
                    itemsRV.visibility = View.VISIBLE
                    moviesVP.visibility = View.VISIBLE
                    genreTAB.visibility = View.VISIBLE
                }
                return true
            }
        })
        return true
    }

    private fun setupTabs(genres: List<GenresDetailsViewModel>) {
        if (moviesVP.visibility == View.GONE && genres.isNotEmpty()) {
            moviesVP.visibility = View.VISIBLE
            genreTAB.visibility = View.VISIBLE
            mainErrorLL.visibility = View.GONE
        }

        val list = genres.toTypedArray().plus(GenresDetailsViewModel(
            id = 10000,
            name = ""
        )).toList()
        adapter = GenresTabsAdapter(supportFragmentManager, list)

        moviesVP.apply {
            adapter = this@MainActivity.adapter
            offscreenPageLimit = 19
        }
        genreTAB.setupWithViewPager(moviesVP)
        genreTAB.getTabAt(19)?.setIcon(R.drawable.ic_star_white_36dp)
    }

    fun onSearchResult(newText: String) {
        val call = RetrofitConfig().tmdbAPI()
        call.getMovieByTitle(newText).enqueue(object : Callback, retrofit2.Callback<MovieResultViewModel> {
            override fun onFailure(call: Call<MovieResultViewModel>, t: Throwable) {
                if (!t.message.isNullOrEmpty()) {
                    mainReloadBTN.visibility = View.VISIBLE
                    onSearchError("SearchView - onFailure error: " + t.message)
                    loadingPB.visibility = View.GONE
                }
            }

            override fun onResponse(
                call: Call<MovieResultViewModel>,
                response: Response<MovieResultViewModel>
            ) {
                mainErrorLL.visibility = View.GONE
                response.body()?.let {
                    val movies: MovieResultViewModel = it
                    setupRecycle(movies.results)
                }
            }

        })
    }

    fun onSearchError(errorMessage: String) {
        Log.e("onSearchError: ", errorMessage)

        if (moviesVP.visibility == View.VISIBLE) {
            moviesVP.visibility = View.GONE
            genreTAB.visibility = View.GONE
            searchLL.visibility = View.GONE
        }
        loadingPB.visibility = View.GONE
        mainErrorLL.visibility = View.VISIBLE
        messageTV.text = errorMessage
    }

    fun onSearchStart() {
        loadingPB.visibility = View.VISIBLE
    }

    private fun reloadGenres() {
        callGenres()
    }

    private fun setupRecycle(movies: List<MovieViewModel>) {
        if (movies.isNotEmpty()) {
            mainErrorLL.visibility = View.GONE
            searchRV?.layoutManager = GridLayoutManager(this, 2)
            searchRV?.adapter = MovieAdapter(movies, this)
            loadingPB.visibility = View.GONE
        } else {
            mainReloadBTN.visibility = View.GONE
            searchRV.visibility = View.GONE
            onSearchError(getString(R.string.notFound))
        }
    }
}
