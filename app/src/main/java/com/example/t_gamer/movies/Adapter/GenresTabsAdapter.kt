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

    val localData = AppDatabase.instance.moviesDAO()

    override fun getItem(position: Int): Fragment {
        if(position < 19) {
            val movieFragment = MoviesFragment.newInstance(genres[position].id)
            moviesFragments.add(movieFragment)
            return movieFragment
        } else {
            val movieFragment = MoviesFragment.newInstance(10000)
            moviesFragments.add(movieFragment)
            return movieFragment
        }
    }

    override fun getPageTitle(position: Int): CharSequence? = genres[position].name

    override fun getCount(): Int = genres.size

    fun setFavorite(){
        fragmentManager.fragments.forEach{
            fragmentManager.apply {
                beginTransaction().detach(it).commitAllowingStateLoss()
                beginTransaction().attach(it).commitAllowingStateLoss()
            }
        }
    }
}