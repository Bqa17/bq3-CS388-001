package com.codepath.moviesproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var abstractTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var languageTextView: TextView
    private lateinit var releaseDateTextView: TextView

    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO: Find the views for the screen
        mediaImageView = findViewById(R.id.mediaImage)
        titleTextView = findViewById(R.id.mediaTitle)
        releaseDateTextView = findViewById(R.id.mediaRelease)
        abstractTextView = findViewById(R.id.mediaAbstract)
        ratingTextView = findViewById(R.id.mediaRating)
        languageTextView = findViewById(R.id.mediaLanguage)

        // TODO: Get the extra from the Intent
        val movie = intent.getSerializableExtra(MOVIE_EXTRA) as Movie

        // TODO: Set the title, byline, and abstract information from the article
        titleTextView.text = movie.title
        abstractTextView.text = movie.overview
        releaseDateTextView.text = "Release Date: ${movie.releaseDate}"
        ratingTextView.text = "Rating: ${movie.rating?.toInt()}/10"
        languageTextView.text = "Language: ${movie.language}"


        Glide.with(this)
            .load(movie.backdropUrl)
            .into(mediaImageView)
    }
}