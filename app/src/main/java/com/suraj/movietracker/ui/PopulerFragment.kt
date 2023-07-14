package com.suraj.movietracker.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.suraj.movietracker.R
import com.suraj.movietracker.apiService.ApiService
import com.suraj.movietracker.databinding.FragmentNowPlayingBinding
import com.suraj.movietracker.databinding.FragmentPopulerBinding
import com.suraj.movietracker.db.SaveMovieData
import com.suraj.movietracker.di.MovieApplication
import com.suraj.movietracker.module.MovieData
import com.suraj.movietracker.repositary.MoviesRepository
import com.suraj.movietracker.ui.adapter.MoviesAdapter
import com.suraj.movietracker.viewModel.MovieViewModel
import com.suraj.movietracker.viewModel.ViewModelFactory
import javax.inject.Inject

class PopulerFragment : Fragment() {


    private var moviesList = ArrayList<MovieData>()

    private var movieSavedList = ArrayList<SaveMovieData>()

    private lateinit var binding: FragmentPopulerBinding

    private lateinit var moviesAdapter: MoviesAdapter

    private lateinit var movieViewModel : MovieViewModel

    @Inject
    lateinit var moviesRepository: MoviesRepository

    var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopulerBinding.inflate(inflater)

        (requireActivity().application as MovieApplication).movieComponent.inject(this)

        initData()

        setPagingInRV()

        setRefreshRV()

        return binding.root
    }



    private fun setRefreshRV() {

        binding.swipeRefreshLayout.setOnRefreshListener {
            moviesList.clear()
            page = 1

            movieViewModel.getPopulerMoviesData(page)

        }

    }





    private fun setPagingInRV() {

        binding.rvPopulerMoviesList.addOnScrollListener(object :
            RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                if (lastVisibleItemPosition == totalItemCount - 3) {
                    page++
                    movieViewModel.getPopulerMoviesData(page)
                }
            }
        })
    }





    @SuppressLint("NotifyDataSetChanged")
    private fun initData() {

        moviesAdapter = MoviesAdapter(moviesList,movieSavedList)

        moviesAdapter.setOnClickListener = SetOnClickListener()

        binding.rvPopulerMoviesList.adapter = moviesAdapter
        binding.rvPopulerMoviesList.layoutManager = GridLayoutManager(requireContext(), 2)


        movieViewModel = ViewModelProvider(
            viewModelStore,
            ViewModelFactory(moviesRepository)
        ).get(MovieViewModel::class.java)

        movieViewModel.moviesList.observe(viewLifecycleOwner) {
            it?.let {
                moviesList.addAll(it)
                moviesAdapter.notifyDataSetChanged()
            }
            binding.swipeRefreshLayout.isRefreshing = false


        }

        movieViewModel.getPopulerMoviesData(page)


        movieViewModel.savedMoviesList.observe(viewLifecycleOwner) {

            moviesAdapter.addAll(it)

            binding.swipeRefreshLayout.isRefreshing = false
        }


    }




    inner class SetOnClickListener : MoviesAdapter.SetOnClickListener {
        override fun setOnClick(position: Int) {



            val intent = Intent(requireContext(),MoviesDetailsActivity::class.java)
            intent.putExtra("moviesData",moviesList[position])
            startActivity(intent)

        }
        override fun deleteVenue(id: Int) {

            movieViewModel.deleteMovieById(id)
        }

        override fun addVenue(saveMovieData: SaveMovieData) {
            movieViewModel.saveMovie(saveMovieData)
        }

    }
}