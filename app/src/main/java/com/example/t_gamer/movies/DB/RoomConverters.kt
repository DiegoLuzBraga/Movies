package com.example.t_gamer.movies.DB


import androidx.room.TypeConverter
import com.example.t_gamer.movies.ViewModel.GenresDetailsViewModel
import com.example.t_gamer.movies.ViewModel.MovieViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomConverters {

    @TypeConverter()
    fun genresIdsToString(genreIds: List<Int>): String {
        return Gson().toJson(genreIds).toString()
    }

    @TypeConverter()
    fun genresIdsFromString(data: String): List<Int>{
        return Gson().fromJson(data, object : TypeToken<List<Int>>() {}.type)
    }

}