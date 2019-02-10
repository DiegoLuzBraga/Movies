package com.example.t_gamer.movies.ViewModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


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
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "movieId")
    var movieId: Int? = null,

    @ColumnInfo(name = "genreId")
    var genreId: Int

)