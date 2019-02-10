package com.example.t_gamer.movies.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.recyclerview.widget.GridLayoutManager
import com.example.t_gamer.movies.Adapter.MovieAdapter
import com.example.t_gamer.movies.API.RetrofitConfig
import com.example.t_gamer.movies.DB.AppDatabase
import com.example.t_gamer.movies.R
import com.example.t_gamer.movies.ViewModel.MovieResultViewModel
import com.example.t_gamer.movies.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.movies_per_genre_fragment.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MoviesFragment : Fragment() {

    companion object {
        fun newInstance(id: Int, position: Int): MoviesFragment {
            val movieFragment = MoviesFragment()
            val args = Bundle()
            args.putInt("id", id)
            args.putInt("position", position)
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
                   // setupRecycle(dbContext.getMoviesByGenres(listOf(arguments!!.getInt("id"))))
                }
            }

            override fun onResponse(call: Call<MovieResultViewModel>, response: Response<MovieResultViewModel>) {
                response.body()?.let {
                    if(arguments!!.getInt("id") == 10000){
                        val favMovies = dbContext.getAllFavoriteMovies()
                        val list = favMovies.map { MovieViewModel(it.id, it.title, it.overview, it.poster_path) }
                        val formatedList: PagedList<MovieViewModel> = list. { PagedList(it.copy()) }
                        setupRecycle()
                    } else {
                        val movies: MovieResultViewModel = it
                        dbContext.insertMovie(movies.results)
                        setupRecycle(movies.results)
                    }
                }
            }
        })
    }

    private fun setupRecycle(movies: LiveData<PagedList<MovieViewModel>>) {
        if (arrayOf(movies).isNotEmpty()) {
            if (movieLL.visibility == View.GONE) {
                movieLL.visibility = View.GONE
            }
            itemsRV?.adapter = MovieAdapter(movies, context!!, arguments!!.getInt("position"))
            itemsRV?.layoutManager = GridLayoutManager(activity, 2)
        } else {
            onError(getString(R.string.movieNotFound))
        }
    }

    private fun onError(message: String) {
        if (movieLL.visibility == View.VISIBLE) {
            movieLL.visibility = View.GONE
        }
        errorLL.visibility = View.VISIBLE
        errorTXT.text = message
    }
}