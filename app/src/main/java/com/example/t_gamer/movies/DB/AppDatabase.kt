package com.example.t_gamer.movies.DB

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.example.t_gamer.movies.ViewModel.GenresDetailsViewModel
import com.example.t_gamer.movies.ViewModel.MovieViewModel

@Database(entities = [MovieViewModel::class, GenresDetailsViewModel::class], version = 1)
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