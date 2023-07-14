package com.suraj.movietracker.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suraj.movietracker.db.SaveMovieData
import com.suraj.movietracker.module.MovieData
import com.suraj.movietracker.repositary.MoviesRepository
import kotlinx.coroutines.launch

class MovieViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {

    private val _moviesList = MutableLiveData<List<MovieData>?>()
    val moviesList: LiveData<List<MovieData>?> get() = _moviesList


    var savedMoviesList: LiveData<List<SaveMovieData>> = moviesRepository.getSaveMovieList()


    fun getNowPlayingMoviesData(page: Int) {
        viewModelScope.launch {
            val result = moviesRepository.getNowPlayingMoviesData(page)
            val movies = result?.body()?.results
            _moviesList.value = movies
        }
    }

    fun getPopulerMoviesData(page: Int) {
        viewModelScope.launch {
            val result = moviesRepository.getPopulerMoviesData(page)
            val movies = result?.body()?.results
            _moviesList.value = movies
        }
    }

    fun getTopRatedMoviesData(page: Int) {
        viewModelScope.launch {
            val result = moviesRepository.getTopRatedMoviesData(page)
            val movies = result?.body()?.results
            _moviesList.value = movies
        }
    }

    fun getUpcomingMoviesData(page: Int) {
        viewModelScope.launch {
            val result = moviesRepository.getUpcomingMoviesData(page)
            val movies = result?.body()?.results
            _moviesList.value = movies
        }
    }

    fun saveMovie(saveMovieData: SaveMovieData) {
        viewModelScope.launch {
            moviesRepository.saveMovie(saveMovieData)
        }
    }

    fun deleteMovieById(id: Int) {
        viewModelScope.launch {
            moviesRepository.deleteMovieFromId(id)
        }
    }


    fun searchMovies(query: String, page: Int) {
        viewModelScope.launch {
            val result = moviesRepository.searchMovies(query, page)
            val movies = result?.body()?.results
            _moviesList.value = movies

            Log.e("searchMovies", result?.body()?.results.toString())

        }
    }

}


