package com.suraj.movietracker.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.suraj.movietracker.module.MovieData

@Database(entities = [MovieData::class], version = 1)
abstract class MovieDatabase :RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao

}