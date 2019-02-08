package com.example.t_gamer.movies.DB

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context
import com.example.t_gamer.movies.ViewModel.FavoriteMoviesViewModel
import com.example.t_gamer.movies.ViewModel.GenresDetailsViewModel
import com.example.t_gamer.movies.ViewModel.MovieViewModel
import com.example.t_gamer.movies.ViewModel.movie_genres

@Database(
    entities = [MovieViewModel::class, GenresDetailsViewModel::class, movie_genres::class, FavoriteMoviesViewModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        @Volatile
        var instance: AppDatabase? = null

        fun initialize(context: Context): AppDatabase = instance ?: synchronized(this) {
            instance ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "movies.db").apply {
                allowMainThreadQueries()
                fallbackToDestructiveMigration()
            }.build().also { instance = it }
        }

    }

    abstract fun moviesDAO(): DAO

}

