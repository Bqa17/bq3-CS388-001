package com.example.moviesproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesproject.R.id

class MovieRecycleViewAdapter(
    private val movies: List<Movie>, // Pluralize for clarity
) : RecyclerView.Adapter<MovieRecycleViewAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_movie, parent, false)
        return MovieViewHolder(view)
    }

    /**
     * ViewHolder for each movie item.
     */
    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: Movie? = null
        val mMovieTitle: TextView = mView.findViewById(id.movie_title)
        val mMovieDescription: TextView = mView.findViewById(id.movie_description)
        val mMoviePoster: ImageView = mView.findViewById(id.movie_poster)

        override fun toString(): String {
            return mMovieTitle.toString()
        }
    }

    /**
     * Bind the Views in the ViewHolder to actual data.
     */
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.mMovieTitle.text = movie.title
        holder.mMovieDescription.text = movie.description

        // Prepend the base URL for the poster image
        val imageUrl = "https://image.tmdb.org/t/p/w500/" + movie.posterImageUrl
        Glide.with(holder.mView)
            .load(imageUrl)
            .centerInside()
            .into(holder.mMoviePoster)
    }

    /**
     * RecyclerView adapters require a getItemCount() method.
     */
    override fun getItemCount(): Int {
        return movies.size
    }
}
