package com.example.t_gamer.movies.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.t_gamer.movies.Fragment.MoviesFragment
import com.example.t_gamer.movies.ViewModel.GenresDetailsViewModel

class GenresTabsAdapter(fragmentManager: FragmentManager, var genres: ArrayList<GenresDetailsViewModel>) :
    FragmentPagerAdapter(fragmentManager) {

    private val moviesFragments: ArrayList<MoviesFragment> = arrayListOf()

    override fun getItem(position: Int): Fragment {
        val movieFragment = MoviesFragment.newInstance(genres[position].id)
        moviesFragments.add(movieFragment)
        return movieFragment
    }

    override fun getPageTitle(position: Int): CharSequence? = genres[position].name

    override fun getCount(): Int = genres.size
}