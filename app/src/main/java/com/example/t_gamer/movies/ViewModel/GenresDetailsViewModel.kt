package com.example.t_gamer.movies.ViewModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Genres")
class GenresDetailsViewModel(

    @PrimaryKey
    @ColumnInfo(name = "genre_id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String
)