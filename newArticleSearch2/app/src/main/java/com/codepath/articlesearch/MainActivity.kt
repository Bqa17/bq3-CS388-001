package com.codepath.articlesearch

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.articlesearch.databinding.ActivityMainBinding
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}

private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = BuildConfig.API_KEY
private const val ARTICLE_SEARCH_URL =
    "https://api.nytimes.com/svc/search/v2/articlesearch.json?api-key=${SEARCH_API_KEY}"
const val PREFS_NAME = "com.codepath.articlesearch.PREFS"
const val PREF_CACHE_DATA = "cache_data"

class MainActivity : AppCompatActivity() {
    private lateinit var articlesRecyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val articles = mutableListOf<DisplayArticle>()
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        articlesRecyclerView = findViewById(R.id.articles)
        articleAdapter = ArticleAdapter(this, articles)
        articlesRecyclerView.adapter = articleAdapter

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                fetchArticlesFromNetwork(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                articleAdapter.filter(newText)
                return true
            }
        })
        searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                // When search bar loses focus, reload articles
                fetchArticlesFromNetwork()
            }
        }

        swipeRefreshLayout.setOnRefreshListener {
            fetchArticlesFromNetwork()
        }

        val isCacheEnabled = sharedPreferences.getBoolean(PREF_CACHE_DATA, true)
        if (isCacheEnabled) {
            lifecycleScope.launch {
                (application as ArticleApplication).db.articleDao().getAll().collect { databaseList ->
                    databaseList.map { entity ->
                        DisplayArticle(
                            entity.headline,
                            entity.articleAbstract,
                            entity.byline,
                            entity.mediaImageUrl
                        )
                    }.also { mappedList ->
                        articles.clear()
                        articles.addAll(mappedList)
                        articleAdapter.notifyDataSetChanged()
                    }
                }
            }
        } else {
            fetchArticlesFromNetwork()
        }

        articlesRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            articlesRecyclerView.addItemDecoration(dividerItemDecoration)
        }
    }

    private fun fetchArticlesFromNetwork(query: String? = null) {
        val encodedQuery = query?.let { Uri.encode(it) } // Encode the query to handle special characters
        val url = if (encodedQuery != null && encodedQuery.isNotEmpty()) {
            "$ARTICLE_SEARCH_URL&q=$encodedQuery"
        } else {
            ARTICLE_SEARCH_URL
        }

        val client = AsyncHttpClient()
        client.get(url, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
                swipeRefreshLayout.isRefreshing = false
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                    val parsedJson = createJson().decodeFromString(
                        SearchNewsResponse.serializer(),
                        json.jsonObject.toString()
                    )

                    parsedJson.response?.docs?.let { list ->
                        lifecycleScope.launch(IO) {
                            (application as ArticleApplication).db.articleDao().deleteAll()
                            (application as ArticleApplication).db.articleDao().insertAll(list.map {
                                ArticleEntity(
                                    headline = it.headline?.main,
                                    articleAbstract = it.abstract,
                                    byline = it.byline?.original,
                                    mediaImageUrl = it.mediaImageUrl
                                )
                            })
                        }
                    }
                    swipeRefreshLayout.isRefreshing = false
                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                    swipeRefreshLayout.isRefreshing = false
                }
            }
        })
    }
}
