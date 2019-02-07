package com.example.t_gamer.movies.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.t_gamer.movies.DB.AppDatabase
import com.example.t_gamer.movies.Fragment.MoviesFragment
import com.example.t_gamer.movies.ViewModel.GenresDetailsViewModel

class GenresTabsAdapter(val fragmentManager: FragmentManager, var genres: List<GenresDetailsViewModel>) :
    FragmentPagerAdapter(fragmentManager) {

    private val moviesFragments: ArrayList<MoviesFragment> = arrayListOf()

    override fun getItem(position: Int): Fragment {
        if(position < 19) {
            val movieFragment = MoviesFragment.newInstance(genres[position].id, position)
            moviesFragments.add(movieFragment)
            return movieFragment
        } else {
            val movieFragment = MoviesFragment.newInstance(10000, position)
            moviesFragments.add(movieFragment)
            return movieFragment
        }
    }

    override fun getPageTitle(position: Int): CharSequence? = if(position < genres.size) genres[position].name else null

    override fun getCount(): Int = genres.size

    fun setFavorite(position: Int?){
        fragmentManager.fragments.forEachIndexed { index, it ->
            fragmentManager.apply {
                if (position != null && position != index) {
                    beginTransaction().detach(it).commitAllowingStateLoss()
                    beginTransaction().attach(it).commitAllowingStateLoss()
                }
            }
        }
    }
}