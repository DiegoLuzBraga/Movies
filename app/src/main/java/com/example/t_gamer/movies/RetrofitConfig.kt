package com.example.t_gamer.movies

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/movie/")
        .addConverterFactory(GsonConverterFactory.create()).build()

    fun tmdbAPI() = retrofit.create(TMDBAPI::class.java)

}