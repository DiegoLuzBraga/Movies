package com.example.t_gamer.movies.DB

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.t_gamer.movies.ViewModel.GenresDetailsViewModel
import com.example.t_gamer.movies.ViewModel.MovieViewModel

@Database(entities = [MovieViewModel::class, GenresDetailsViewModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDAO(): DAO

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, "movies.db"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}