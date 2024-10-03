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
import org.json.JSONObject

// --------------------------------//
// CHANGE THIS TO BE YOUR API KEY  //
// --------------------------------//
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

/*
 * The class for the only fragment in the app, which contains the progress bar,
 * recyclerView, and performs the network calls to the NY Times API.
 */
class MoviesFragment : Fragment(){

    /*
     * Constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(layout.fragment_movie_list, container, false)

        // Initialize the progress bar and recycler view
        val progressBar = view.findViewById<ContentLoadingProgressBar>(R.id.progressBar)
        val recyclerView = view.findViewById<RecyclerView>(R.id.list)

        // Set the layout manager for the RecyclerView (using LinearLayoutManager)
        recyclerView.layoutManager = GridLayoutManager(view.context, 2)
        recyclerView.adapter = MovieRecycleViewAdapter(emptyList())

        // Call the method to update the adapter
        updateAdapter(progressBar, recyclerView)

        return view
    }


    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api-key"] = API_KEY
        // Using the client, perform the HTTP request

        client[
            "https://api.themoviedb.org/3/movie/now_playing?&api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
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
                    // The wait for a response is over
                    progressBar.hide()

                    val resultsJSON: JSONObject = json.jsonObject.get("results") as JSONObject
                    val moviesRawJSON: String = resultsJSON.get("movies").toString()
                    val gson = Gson()
                    val arrayMovieType =
                        object : com.google.gson.reflect.TypeToken<List<Movie>>() {}.type

                    //TODO - Parse JSON into Models
                    val models: List<Movie> = gson.fromJson(moviesRawJSON, arrayMovieType)
                    recyclerView.adapter = MovieRecycleViewAdapter(models)

                    // Look for this in Logcat:
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
                    // The wait for a response is over
                    progressBar.hide()

                    // If the error is not null, log it!
                    t?.message?.let {
                        Log.e("MovieFragment", errorResponse)
                    }
                }
            }]
    }

    /*
     * What happens when a particular book is clicked.
     */
//    override fun onItemClick(item: Movie) {
//        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
//    }

}