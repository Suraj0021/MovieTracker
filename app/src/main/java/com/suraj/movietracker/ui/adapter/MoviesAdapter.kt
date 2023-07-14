package com.suraj.movietracker.ui.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.suraj.movietracker.R
import com.suraj.movietracker.databinding.MovieViewBinding
import com.suraj.movietracker.module.MovieData

class MoviesAdapter(
    private val moviesList: ArrayList<MovieData>,
    private val movieSaveList: ArrayList<MovieData>
) : RecyclerView.Adapter<MoviesAdapter.MoviesHolder>() {


    interface SetOnClickListener {
        fun setOnClick(position: Int)

        fun deleteVenue(id: Int)

        fun addVenue(saveMovieData: MovieData)

    }

    var setOnClickListener: SetOnClickListener? = null

    fun setOnClickListener(listener: SetOnClickListener) {
        setOnClickListener = listener
    }


    class MoviesHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding: MovieViewBinding = MovieViewBinding.bind(view)

    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {

        val view = MoviesHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_view, parent, false)
        )

        return view
    }

    override fun getItemCount() = moviesList.size

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {

        holder.binding.imgMoviePoster.setOnClickListener {
            setOnClickListener?.setOnClick(position)
        }

        val data = moviesList[position]

        holder.binding.movies = data

        Picasso.get()
            .load("https://image.tmdb.org/t/p/w200/${data.poster_path}")
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(holder.binding.imgMoviePoster)

            if (isMovieSaved(data.id)) {
                holder.binding.imgFavourite.setImageResource(R.drawable.ic_save)
            } else {
                holder.binding.imgFavourite.setImageResource(R.drawable.ic_notsave)
            }


        holder.binding.imgFavourite.setOnClickListener {

            if (isMovieSaved(data.id)) {

                setOnClickListener?.deleteVenue(data.id)

                holder.binding.imgFavourite.setImageResource(R.drawable.ic_notsave)

            } else {

                val saveMovieData = MovieData(
                    data.id,
                    data.original_language,
                    data.original_title,
                    data.overview,
                    data.popularity,
                    data.poster_path,
                    data.release_date,
                    data.title,
                    data.vote_average,
                    data.vote_count
                )

                setOnClickListener?.addVenue(saveMovieData)

                holder.binding.imgFavourite.setImageResource(R.drawable.ic_save)

            }
        }

    }


    private fun isMovieSaved(movieId: Int): Boolean {
        for (savedMovie in movieSaveList) {
            if (savedMovie.id == movieId) {
                return true
            }
        }
        return false
    }




    fun addAll(moviesList: List<MovieData>) {

        movieSaveList.clear()
        movieSaveList.addAll(moviesList)

    }

    fun addAll2(moviesList2: List<MovieData>?) {

        if (moviesList2 != null) {
            moviesList.addAll(moviesList2)
        }
        notifyDataSetChanged()
    }

}
