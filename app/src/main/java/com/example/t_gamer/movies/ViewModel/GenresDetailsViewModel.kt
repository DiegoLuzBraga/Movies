package com.example.t_gamer.movies.ViewModel

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Genres")
class GenresDetailsViewModel(

    @PrimaryKey
    @ColumnInfo(name = "genre_id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String
)