package com.example.t_gamer.movies.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.app.Fragment
import androidx.appcompat.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.t_gamer.movies.API.RetrofitConfig
import com.example.t_gamer.movies.Adapter.MovieAdapter
import com.example.t_gamer.movies.R
import com.example.t_gamer.movies.ViewModel.MovieResultViewModel
import com.example.t_gamer.movies.ViewModel.MovieViewModel
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class SearchFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.movie_card, container, false)
    }



    fun onSearchStart() {

    }





}
