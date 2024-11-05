package com.codepath.articlesearch

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var addButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: EntryAdapter
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addButton = findViewById(R.id.addButton)
        recyclerView = findViewById(R.id.exerciseListRV)
        db = AppDatabase.getDatabase(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = EntryAdapter(emptyList())
        recyclerView.adapter = adapter

        loadExercises()

        addButton.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadExercises() {
        lifecycleScope.launch {
            db.exerciseDao().getAll().collect { sessions ->
                adapter.setSessions(sessions)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadExercises()
    }
}
