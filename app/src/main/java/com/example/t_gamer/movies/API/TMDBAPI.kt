package com.example.t_gamer.movies.API

import com.example.t_gamer.movies.ViewModel.GenresViewModel
import com.example.t_gamer.movies.ViewModel.MovieResultViewModel
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Query

interface TMDBAPI {
    @GET("movie/popular?api_key=548202cec0f96e480f6e8aa312dd3d3b&language=pt-BR")
    fun getPopular(): Call<MovieResultViewModel>

    @GET("genre/movie/list?api_key=548202cec0f96e480f6e8aa312dd3d3b&language=pt-BR")
    fun getGenres(): Call<GenresViewModel>

    @GET("discover/movie?api_key=548202cec0f96e480f6e8aa312dd3d3b&language=pt-BR&")
    fun getMoviesByGenre(
        @Query("with_genres") id: Int
    ): Call<MovieResultViewModel>

    @GET("search/movie?api_key=548202cec0f96e480f6e8aa312dd3d3b&language=pt-BR&include_adult=false&")
    fun getMovieByTitle(
        @Query("query") title: String
    ) : Call<MovieResultViewModel>
}