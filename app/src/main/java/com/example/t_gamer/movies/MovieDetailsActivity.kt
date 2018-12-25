package com.example.t_gamer.movies

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_movie_details.*

class MovieDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)
        val overview: String = intent.getStringExtra("overview")
        val image: String = intent.getStringExtra("image")
        supportActionBar?.title = intent.getStringExtra("title")

        if (overview.isNotEmpty()) {
            overviewTXT.text = overview
        } else {
            overviewTXT.text = getString(R.string.descriptionUnavailable)
        }
        Picasso.get().load(image).into(detailsIMG)
    }
}
