package com.suraj.movietracker.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SaveMovieData::class], version = 1)
abstract class MovieDatabase :RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao

}