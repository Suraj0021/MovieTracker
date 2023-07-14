package com.suraj.movietracker.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suraj.movietracker.databinding.FragmentNowPlayingBinding
import com.suraj.movietracker.di.MovieApplication
import com.suraj.movietracker.module.MovieData
import com.suraj.movietracker.repositary.MoviesRepository
import com.suraj.movietracker.ui.adapter.MoviesAdapter
import com.suraj.movietracker.viewModel.MovieViewModel
import com.suraj.movietracker.viewModel.ViewModelFactory
import javax.inject.Inject

class NowPlayingFragment : Fragment() {

    private var moviesList = ArrayList<MovieData>()
    private var movieSavedList = ArrayList<MovieData>()
    private lateinit var binding: FragmentNowPlayingBinding
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var movieViewModel: MovieViewModel

    @Inject
    lateinit var moviesRepository: MoviesRepository

    private var page = 1
    private var searchPage = 1

    private var isSearchOn = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNowPlayingBinding.inflate(inflater)
        (requireActivity().application as MovieApplication).movieComponent.inject(this)

        initData()
        setPagingInRV()
        setRefreshRV()
        searchMovies()

        return binding.root
    }

    private fun setRefreshRV() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            moviesList.clear()
            page = 1
            searchPage = 1

            if (isSearchOn){
                movieViewModel.searchMovies(binding.edtSearch.text.toString(),searchPage)
            }else{
                movieViewModel.getNowPlayingMoviesData(page)
            }
        }
    }


    private fun setPagingInRV() {
        binding.rvNowPlayingMoviesList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItemPosition == totalItemCount - 3) {
                    if (isSearchOn) {
                        searchPage++
                        movieViewModel.searchMovies(binding.edtSearch.text.toString(), searchPage)
                    } else {
                        page++
                        movieViewModel.getNowPlayingMoviesData(page)
                    }
                }
            }
        })
    }



    @SuppressLint("NotifyDataSetChanged")
    private fun initData() {
        moviesAdapter = MoviesAdapter(moviesList, movieSavedList)
        moviesAdapter.setOnClickListener = SetOnClickListener()

        binding.rvNowPlayingMoviesList.adapter = moviesAdapter
        binding.rvNowPlayingMoviesList.layoutManager = GridLayoutManager(requireContext(), 2)

        movieViewModel = ViewModelProvider(
            viewModelStore, ViewModelFactory(moviesRepository)
        ).get(MovieViewModel::class.java)

        movieViewModel.moviesList.observe(viewLifecycleOwner) { movies ->
            movies?.let {
                moviesAdapter.addAll2(it)
            }
            binding.swipeRefreshLayout.isRefreshing = false
        }

        movieViewModel.getNowPlayingMoviesData(page)



        movieViewModel.savedMoviesList.observe(viewLifecycleOwner){
                movies ->
            movies?.apply {
                moviesAdapter.addAll(movies)
            }
        }
    }



    private fun searchMovies() {


        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s.toString().length < 3) {

                    isSearchOn = false

                    movieViewModel.getNowPlayingMoviesData(1)

                } else {

                    isSearchOn = true

                    moviesList.clear()

                    movieViewModel.searchMovies(s.toString(), 1)

                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }




    inner class SetOnClickListener : MoviesAdapter.SetOnClickListener {
        override fun setOnClick(position: Int) {
            val intent = Intent(requireContext(), MoviesDetailsActivity::class.java)
            intent.putExtra("moviesData", moviesList[position])
            startActivity(intent)
        }

        override fun deleteVenue(id: Int) {
            movieViewModel.deleteMovieById(id)
        }

        override fun addVenue(saveMovieData: MovieData) {
            movieViewModel.saveMovie(saveMovieData)
        }
    }
}
