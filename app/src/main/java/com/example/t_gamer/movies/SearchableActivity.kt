package com.example.t_gamer.movies

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu

class SearchableActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu?.findItem(R.id.searchBTN)
        var searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

        })
        return super.onCreateOptionsMenu(menu)
    }
}