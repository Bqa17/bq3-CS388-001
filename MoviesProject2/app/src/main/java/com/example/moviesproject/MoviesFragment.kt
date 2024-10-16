package com.example.moviesproject

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.moviesproject.R.*


import com.google.gson.Gson
import okhttp3.Headers
import org.json.JSONArray

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"


class MoviesFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(layout.fragment_movie_list, container, false)
        val progressBar = view.findViewById<ContentLoadingProgressBar>(R.id.progressBar)
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)

        recyclerView.layoutManager = GridLayoutManager(view.context, 1)
        updateAdapter(progressBar, recyclerView)

        return view
    }

    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = API_KEY
        // Using the client, perform the HTTP request

        client[
            "https://api.themoviedb.org/3/movie/top_rated?",
            params,
            object : JsonHttpResponseHandler() {
                /*
                 * The onSuccess function gets called when
                 * HTTP response status is "200 OK"
                 */
                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JsonHttpResponseHandler.JSON
                ) {

                    progressBar.hide()
                    val resultsJSON: JSONArray = json.jsonObject.get("results") as JSONArray
                    val moviesRawJSON: String = resultsJSON.toString()

                    val gson = Gson()
                    val arrayMovieType = object : com.google.gson.reflect.TypeToken<List<Movie>>() {}.type
                    val models: List<Movie> = gson.fromJson(moviesRawJSON, arrayMovieType)
                    recyclerView.adapter = MovieRecycleViewAdapter(models)

                    Log.d("MovieFragment", "response successful")
                }

                /*
                 * The onFailure function gets called when
                 * HTTP response status is "4XX" (eg. 401, 403, 404)
                 */
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    t: Throwable?
                ) {

                    progressBar.hide()

                    // If the error is not null, log it!
                    t?.message?.let {
                        Log.e("MovieFragment", errorResponse)
                    }
                }
            }]
    }

}