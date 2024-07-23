package com.kaankilic.film.RoomDB

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kaankilic.film.model.Movie

@Dao
interface MovieDao {
    @Insert
    suspend fun insertAll(vararg  movie : Movie): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: Movie)


    @Query("SELECT * FROM movie WHERE uuid = :movieId")
    suspend fun getMovie(movieId : Int) : Movie

    @Query("DELETE FROM movie")
    suspend fun deleteAll()

    @Query("SELECT * FROM movie")
    suspend fun getAllMovie(): List<Movie>
    @Delete
    suspend fun deleteMovie(movie: Movie)
}