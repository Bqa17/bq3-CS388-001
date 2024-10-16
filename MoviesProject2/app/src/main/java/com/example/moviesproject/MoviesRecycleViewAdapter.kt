package com.codepath.articlesearch

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesproject.Movie
import com.example.moviesproject.R



const val MOVIE_EXTRA = "MOVIE_EXTRA"
private const val TAG = "ArticleAdapter"

class MoviesRecycleViewAdapter(private val context: Context, private val movies: List<Movie>) :
    RecyclerView.Adapter<MoviesRecycleViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_movie, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movies = movies[position]
        holder.bind(movies)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val mediaImageView = itemView.findViewById<ImageView>(R.id.mediaImage)
        private val titleTextView = itemView.findViewById<TextView>(R.id.mediaTitle)
        private val abstractTextView = itemView.findViewById<TextView>(R.id.mediaAbstract)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: Movie) {
            titleTextView.text = movie.title
            abstractTextView.text = movie.description

            Glide.with(context)
                .load(movie.multimedia)
                .into(mediaImageView)
        }


        override fun onClick(v: View?) {
            // TODO: Get selected article
            val movie = movies[0]

            // TODO: Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(MOVIE_EXTRA, movie)
            context.startActivity(intent)
        }
    }
}