package com.example.t_gamer.movies

data class MovieResultViewModel (
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: ArrayList<MovieViewModel>
)