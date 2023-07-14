package com.suraj.movietracker.repositary

import androidx.lifecycle.LiveData
import com.suraj.movietracker.apiService.ApiService
import com.suraj.movietracker.db.MoviesDao
import com.suraj.movietracker.module.MovieData
import com.suraj.movietracker.module.MovieResponse
import retrofit2.Response
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val apiService: ApiService,
    private val moviesDao: MoviesDao
) {

    suspend fun getNowPlayingMoviesData(page: Int): Response<MovieResponse>? {
        return try {
            apiService.getNowPlayingMoviesData(page)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getPopulerMoviesData(page: Int): Response<MovieResponse>? {
        return try {
            apiService.getPopulerMoviesData(page)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getTopRatedMoviesData(page: Int): Response<MovieResponse>? {
        return try {
            apiService.getTopRatedMoviesData(page)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getUpcomingMoviesData(page: Int): Response<MovieResponse>? {
        return try {
            apiService.getUpcomingMoviesData(page)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun saveMovie(movieData: MovieData) {
        moviesDao.addMovieData(movieData)
    }

    fun getSaveMovieList(): LiveData<List<MovieData>> {
        return moviesDao.getMovies()
    }

    suspend fun deleteMovieFromId(id: Int) {
        moviesDao.deleteMovieById(id)
    }



    suspend fun searchMovies(query: String, page: Int) : Response<MovieResponse>?{
     return   try {
            apiService.searchMovies(query, page = page)
        } catch (e: Exception) {
            null
        }
    }
}
