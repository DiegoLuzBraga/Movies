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

    // private lateinit var moviesArray: ArrayList<MovieViewModel>

    companion object {
        fun newInstance(id: Int): MoviesFragment {
            val movieFragment = MoviesFragment()
            val args = Bundle()
            args.putInt("id", id)
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
        MainSRL?.isRefreshing = true
        val call = RetrofitConfig().tmdbAPI().getMoviesByGenre(arguments!!.getInt("id"))

        call.enqueue(object : Callback, retrofit2.Callback<MovieResultViewModel> {
            override fun onFailure(call: Call<MovieResultViewModel>, t: Throwable) {
                if (!t.message.isNullOrEmpty()) {
                    Log.e("onFailure error", t.message)
                }
                MainSRL?.isRefreshing = false
            }

            override fun onResponse(call: Call<MovieResultViewModel>, response: Response<MovieResultViewModel>) {
                response.body()?.let {
                    val movies: MovieResultViewModel = it
                    // moviesArray.plus(movies.results)
                    setupRecycle(movies.results)
                }
                MainSRL?.isRefreshing = false
            }
        })
    }

    private fun setupRecycle(movies: List<MovieViewModel>) {
        itemsRV?.adapter = MovieAdapter(movies)
        itemsRV?.layoutManager = GridLayoutManager(activity, 2)
    }
}