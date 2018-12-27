package com.example.t_gamer.movies

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class GenresTabsAdapter(fragmentManager: FragmentManager, var genres: ArrayList<GenresDetailsViewModel>) :
    FragmentPagerAdapter(fragmentManager) {

    private val moviesFragments: ArrayList<MoviesFragment> = arrayListOf()

    override fun getItem(position: Int): Fragment {
        val movieFragment = MoviesFragment.newInstance(genres[position].id)
        moviesFragments.add(movieFragment)
        return movieFragment
    }

    override fun getCount(): Int {
        return genres.size
    }
}