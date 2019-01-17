package com.example.t_gamer.movies.ViewModel

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(
    tableName = "movie_genres",
    foreignKeys =
    [ForeignKey(
        entity = MovieViewModel::class,
        parentColumns = arrayOf("movie_id"),
        childColumns = arrayOf("movieId"),
        onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = GenresDetailsViewModel::class,
        parentColumns = arrayOf("genre_id"),
        childColumns = arrayOf("genreId"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class movie_genres(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "movieId")
    var movieId: Int,

    @ColumnInfo(name = "genreId")
    var genreId: Int

)