package com.example.t_gamer.movies.ViewModel

data class MovieResultViewModel (
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: List<MovieViewModel>
)