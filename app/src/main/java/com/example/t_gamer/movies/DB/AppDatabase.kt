package com.example.t_gamer.movies.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.t_gamer.movies.ViewModel.FavoriteMoviesViewModel
import com.example.t_gamer.movies.ViewModel.GenresDetailsViewModel
import com.example.t_gamer.movies.ViewModel.MovieViewModel
import com.example.t_gamer.movies.ViewModel.movie_genres

@Database(entities = [MovieViewModel::class, GenresDetailsViewModel::class, movie_genres::class, FavoriteMoviesViewModel::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        lateinit var instance: AppDatabase
            private set

        fun initialize(context: Context) {
            instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "movies.db").apply {
                allowMainThreadQueries()
                fallbackToDestructiveMigration()
            }.build()
        }
    }

    abstract fun moviesDAO(): DAO

}