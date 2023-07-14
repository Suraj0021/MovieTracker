package com.suraj.movietracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.suraj.movietracker.R
import com.suraj.movietracker.databinding.ActivityMoviesDetailsBinding
import com.suraj.movietracker.db.SaveMovieData
import com.suraj.movietracker.di.MovieApplication
import com.suraj.movietracker.module.MovieData
import com.suraj.movietracker.repositary.MoviesRepository
import com.suraj.movietracker.viewModel.MovieViewModel
import com.suraj.movietracker.viewModel.ViewModelFactory
import javax.inject.Inject

class MoviesDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoviesDetailsBinding

    @Inject
    lateinit var moviesRepository: MoviesRepository

    private lateinit var movieViewModel: MovieViewModel

    private var savedMoviesList = ArrayList<SaveMovieData>()

    private lateinit var movieData: MovieData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMoviesDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as MovieApplication).movieComponent.inject(this)



        movieData = intent.getSerializableExtra("moviesData") as MovieData

        Picasso.get()
            .load("https://image.tmdb.org/t/p/w200/${movieData.poster_path}")
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(binding.imgMoviePoster)

        binding.movieData = movieData

        movieViewModel = ViewModelProvider(
            viewModelStore,
            ViewModelFactory(moviesRepository)
        ).get(MovieViewModel::class.java)



        movieViewModel.savedMoviesList.observe(this) { savedMovies ->

            savedMoviesList.clear()

            savedMoviesList.addAll(savedMovies)

            if (isSave(savedMoviesList)) {
                binding.imgSave.setImageResource(R.drawable.ic_save)
            } else {
                binding.imgSave.setImageResource(R.drawable.ic_notsave)
            }

            binding.imgSave.apply {

                setOnClickListener {
                    if (isSave(savedMoviesList)) {
                        movieViewModel.deleteMovieById(movieData.id)
                        binding.imgSave.setImageResource(R.drawable.ic_notsave)
                    } else {
                        movieViewModel.saveMovie(
                            SaveMovieData(
                                movieData.id,
                                movieData.original_language,
                                movieData.original_title,
                                movieData.overview,
                                movieData.popularity,
                                movieData.poster_path,
                                movieData.release_date,
                                movieData.title,
                                movieData.vote_average,
                                movieData.vote_count
                            )
                        )
                        binding.imgSave.setImageResource(R.drawable.ic_save)
                    }
                }
            }

        }


    }

    fun isSave(savedMoviesList: ArrayList<SaveMovieData>): Boolean {
        for (m in this.savedMoviesList) {
            if (m != null && m.id == movieData.id) {
                return true
            }
        }
        return false
    }
}
