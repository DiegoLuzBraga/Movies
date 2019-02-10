package com.example.t_gamer.movies.ViewModel

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class MovieResultViewModel (
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: LiveData<PagedList<MovieViewModel>>
)