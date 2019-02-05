package com.example.t_gamer.movies.ViewModel

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity(tableName = "Movies")
data class MovieViewModel(

    @PrimaryKey
    @ColumnInfo(name = "movie_id")
    var id: Int,

    var genre_ids: List<Int>,

    @ColumnInfo(name = "title")
    var title: String,

    @ColumnInfo(name = "overview")
    var overview: String,

    @ColumnInfo(name = "poster_path")
    var poster_path: String?,

    @Expose()
    var isFavorite: Int = 0

)