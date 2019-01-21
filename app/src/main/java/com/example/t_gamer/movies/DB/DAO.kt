package com.example.t_gamer.movies.DB

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.t_gamer.movies.ViewModel.GenresDetailsViewModel
import com.example.t_gamer.movies.ViewModel.MovieViewModel
import com.example.t_gamer.movies.ViewModel.movie_genres

@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genres: List<GenresDetailsViewModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovieGenres(movieGenre: movie_genres)

    @Query(
        """
        UPDATE movie_genres
        SET movieId = :movieId
        WHERE movie_genres.genreId = :genreId
    """
    )
    fun updateMovieGenres(movieId: Int, genreId: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movies: List<MovieViewModel>)

    @Query("Select * from Genres")
    fun getGenres(): List<GenresDetailsViewModel>

    @Query(
        """
        SELECT *
        FROM Movies
        INNER JOIN movie_genres ON movie_genres.genreId = (Movies.genre_ids)
        WHERE :genre_id IN (genre_ids)"""
    )
    fun getMoviesByGenres(genre_id: List<Int>): List<MovieViewModel>

    @Query("SELECT * FROM movie_genres")
    fun getAll(): List<movie_genres>

    @Query("SELECT * FROM Movies")
    fun getAllMovies(): List<MovieViewModel>
}