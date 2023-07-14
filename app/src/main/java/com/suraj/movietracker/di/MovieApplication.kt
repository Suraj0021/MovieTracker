package com.suraj.movietracker.di

import android.app.Application

class MovieApplication : Application() {

    lateinit var movieComponent: MovieComponent

    override fun onCreate() {
        super.onCreate()

        movieComponent = DaggerMovieComponent.builder().networkModule(NetworkModule(applicationContext)).build()


    }

}