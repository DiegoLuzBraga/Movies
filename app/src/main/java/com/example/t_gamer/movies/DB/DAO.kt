package com.example.t_gamer.movies.DB

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.t_gamer.movies.ViewModel.FavoriteMoviesViewModel
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
    fun insertMovie(movies: LiveData<PagedList<MovieViewModel>>)

    @Query("Select * from Genres")
    fun getGenres(): List<GenresDetailsViewModel>

//    @Query(
//        """
//        SELECT *
//        FROM movies
//        INNER JOIN movie_genres ON movie_genres.genreId = (movies.genre_ids)
//        WHERE :genre_id IN (genre_ids)"""
//    )
//    fun getMoviesByGenres(genre_id: List<Int>): List<MovieViewModel>

    @Query("SELECT * FROM movie_genres")
    fun getAll(): List<movie_genres>

    @Query("SELECT movie_id, title, overview, poster_path FROM movies")
    fun getAllMovies(): List<MovieViewModel>

    @Query("""
            SELECT movie_id, title, overview, poster_path
            FROM favorite
    """)
    fun getAllFavoriteMovies(): List<FavoriteMoviesViewModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: FavoriteMoviesViewModel)

    @Query("""
        DELETE FROM favorite
        WHERE favorite.movie_id = (:id)
    """)
    fun deleteFavoritedMovie(id: Int)
}