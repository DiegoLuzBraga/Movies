package com.example.t_gamer.movies
import retrofit2.http.GET
import retrofit2.Call

interface TMDBAPI {
    @GET("popular?api_key=548202cec0f96e480f6e8aa312dd3d3b&language=pt-BR")
    fun getPopular() : Call<List<MovieViewModel>>
}