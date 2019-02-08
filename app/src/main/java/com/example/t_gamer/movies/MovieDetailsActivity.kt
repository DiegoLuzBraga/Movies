package com.example.t_gamer.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.t_gamer.movies.DB.AppDatabase
import com.example.t_gamer.movies.ViewModel.FavoriteMoviesViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    val localData = AppDatabase.instance.moviesDAO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        setSupportActionBar(moviesDetailsTB)

        val movieId: Int = intent.getIntExtra("id", 0)

        val movieOverview: String = intent.getStringExtra("overview")

        val movieImage: String = intent.getStringExtra("image")

        val isFavorite: Int = intent.getIntExtra("isFavorite", 0)

        supportActionBar?.setDisplayShowTitleEnabled(false)

        moviesDetailsTV.text = intent.getStringExtra("title")

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (movieOverview.isNotEmpty()) {
            overviewTXT.text = movieOverview
        } else {
            overviewTXT.text = getString(R.string.descriptionUnavailable)
        }
        Picasso.get().load(movieImage).into(detailsIMG)

        if(isFavorite == 0) {
            favoriteDetailsIMG.setImageResource(R.drawable.ic_star_border_white_36dp)
        } else {
            favoriteDetailsIMG.setImageResource(R.drawable.ic_star_white_36dp)
        }

        favoriteDetailsIMG.setOnClickListener {
            val favMovies = localData.getAllFavoriteMovies()
            if (favMovies.map { it.id }.contains(movieId)) {
                localData.deleteFavoritedMovie(movieId)
                favoriteDetailsIMG.setImageResource(R.drawable.ic_star_border_white_36dp)
            } else {
                localData.insertFavorite(
                    FavoriteMoviesViewModel(
                        id = movieId,
                        overview = movieOverview,
                        poster_path = movieImage,
                        title = intent.getStringExtra("title")
                    )
                )
                favoriteDetailsIMG.setImageResource(R.drawable.ic_star_white_36dp)
            }

        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return false
    }
}
