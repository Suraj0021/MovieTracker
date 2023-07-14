package com.suraj.movietracker.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suraj.movietracker.repositary.MoviesRepository


class ViewModelFactory(private val moviesRepository: MoviesRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(moviesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}