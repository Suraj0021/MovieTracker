package com.suraj.movietracker.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.suraj.movietracker.databinding.FragmentFavouriteBinding
import com.suraj.movietracker.db.SaveMovieData
import com.suraj.movietracker.di.MovieApplication
import com.suraj.movietracker.repositary.MoviesRepository
import com.suraj.movietracker.ui.adapter.SavedMoviesAdapter
import com.suraj.movietracker.viewModel.MovieViewModel
import com.suraj.movietracker.viewModel.ViewModelFactory
import javax.inject.Inject

class FavouriteFragment : Fragment() {

    private var moviesList = ArrayList<SaveMovieData>()

    private lateinit var binding: FragmentFavouriteBinding

    private lateinit var moviesAdapter: SavedMoviesAdapter

    private lateinit var movieViewModel: MovieViewModel

    @Inject
    lateinit var moviesRepository: MoviesRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavouriteBinding.inflate(inflater)


        (requireActivity().application as MovieApplication).movieComponent.inject(this)

        initView()

        initData()

        setRefreshRV()

        return binding.root
    }

    fun initView(){
        moviesAdapter = SavedMoviesAdapter(moviesList)

        moviesAdapter.setOnClickListener = SetOnClickListener()

        binding.rvFavouriteMoviesList.adapter = moviesAdapter
        binding.rvFavouriteMoviesList.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        movieViewModel = ViewModelProvider(
            viewModelStore,
            ViewModelFactory(moviesRepository)
        ).get(MovieViewModel::class.java)

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initData() {


        movieViewModel.savedMoviesList.observe(viewLifecycleOwner) {

            moviesAdapter.addAll(it)

            binding.swipeRefreshLayout.isRefreshing = false
        }

    }

    inner class SetOnClickListener : SavedMoviesAdapter.SetOnClickListener {
        override fun setOnClick(position: Int, data: SaveMovieData) {

            movieViewModel.deleteMovieById(data.id)

            moviesAdapter.notifyDataSetChanged()

        }
    }

    private fun setRefreshRV() {

        binding.swipeRefreshLayout.setOnRefreshListener {
            moviesList.clear()
            initData()
        }

    }


}