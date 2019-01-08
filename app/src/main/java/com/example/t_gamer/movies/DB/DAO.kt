package com.example.t_gamer.movies.DB

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.t_gamer.movies.ViewModel.GenresDetailsViewModel
import com.example.t_gamer.movies.ViewModel.MovieViewModel

@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genres: List<GenresDetailsViewModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: List<MovieViewModel>)

    @Query("Select * from Genres")
    fun getGenres() : List<GenresDetailsViewModel>

    @Query("Select * from Movies Where genre_ids = :genre_id")
    fun getMoviesByGenres(genre_id: List<Int>) : List<MovieViewModel>

    @Query("SELECT * FROM Movies")
    fun getAllMovies(): List<MovieViewModel>

}