package com.suraj.movietracker.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.suraj.movietracker.apiService.ApiService
import com.suraj.movietracker.db.MovieDatabase
import com.suraj.movietracker.db.MoviesDao
import com.suraj.movietracker.repositary.MoviesRepository
import com.suraj.movietracker.util.Constants
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(private val context: Context) {
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun providesVenueApi(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesRepository(apiService: ApiService,moviesDao: MoviesDao): MoviesRepository {
        return MoviesRepository(apiService,moviesDao)
    }


    @Singleton
    @Provides
    fun provideMoviesDao(movieDatabase: MovieDatabase): MoviesDao {
        return movieDatabase.getMoviesDao()
    }

    @Provides
    @Singleton
    fun provideMoviesDatabase(context: Context): MovieDatabase {
        return Room.databaseBuilder(context, MovieDatabase::class.java, "moviesDB")
            .build()
    }

    @Provides
    fun provideApplicationContext(): Context {
        return context.applicationContext
    }


}