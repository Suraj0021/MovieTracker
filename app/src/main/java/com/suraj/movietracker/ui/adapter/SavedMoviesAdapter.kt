package com.suraj.movietracker.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.suraj.movietracker.R
import com.suraj.movietracker.databinding.SavedMovieViewBinding
import com.suraj.movietracker.db.SaveMovieData

class SavedMoviesAdapter(private val movieSaveList: ArrayList<SaveMovieData>) :
    RecyclerView.Adapter<SavedMoviesAdapter.SavedMoviesHolder>() {


    interface SetOnClickListener {
        fun setOnClick(position: Int, data: SaveMovieData)
    }

    var setOnClickListener: SetOnClickListener? = null

    fun setOnClickListener(listener: SetOnClickListener) {
        setOnClickListener = listener
    }


    class SavedMoviesHolder(view: View) : RecyclerView.ViewHolder(view) {

        val binding: SavedMovieViewBinding = SavedMovieViewBinding.bind(view)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SavedMoviesHolder {

        val view = SavedMoviesHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.saved_movie_view, parent, false)
        )

        return view
    }

    override fun onBindViewHolder(holder: SavedMoviesHolder, position: Int) {


        val data = movieSaveList[position]

        holder.binding.movies = data

        Picasso.get()
            .load("https://image.tmdb.org/t/p/w200/${data.poster_path}")
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .into(holder.binding.imgMoviePoster)

        holder.binding.imgSave.setOnClickListener {
            setOnClickListener?.setOnClick(position, data)
        }

    }

    override fun getItemCount() = movieSaveList.size

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(moviesList: List<SaveMovieData>) {

        movieSaveList.clear()
        movieSaveList.addAll(moviesList)
        notifyDataSetChanged()

    }

}