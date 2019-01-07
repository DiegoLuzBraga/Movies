package com.example.t_gamer.movies.Core

import android.app.Application
import com.example.t_gamer.movies.DB.AppDatabase

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        AppDatabase.initialize(this)
    }
}