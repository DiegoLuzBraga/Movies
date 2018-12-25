package com.example.t_gamer.movies

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class GenresTabsAdapter(fragmentManager: FragmentManager, moviesFragment: List<MoviesFragment>,genres: List<GenresViewModel>) :
    FragmentPagerAdapter(fragmentManager) {

    private var genresFragment: List<MoviesFragment> = moviesFragment
    private var genresList: List<GenresViewModel> = genres

    private fun add(fragment: MoviesFragment){
        genresFragment.plus(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return genresFragment.get(position)
    }

    override fun getCount(): Int {
        return genresList.size
    }
}