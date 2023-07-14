package com.suraj.movietracker.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.suraj.movietracker.module.MovieData
import dagger.Module

@Module
@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieData(saveMovieData: SaveMovieData)


    @Query("SELECT * FROM movies")
    fun getMovies(): LiveData<List<SaveMovieData>>


    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Int)

}