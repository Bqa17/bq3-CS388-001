package com.codepath.articlesearch

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executors


private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {
    private lateinit var entryTitleInput: EditText
    private lateinit var entryInfoInput: EditText
    private lateinit var saveButton: Button
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        entryTitleInput = findViewById(R.id.entryInput)
        entryInfoInput = findViewById(R.id.infoInput)
        saveButton = findViewById(R.id.saveButton)

        db = AppDatabase.getDatabase(this)

        saveButton.setOnClickListener {
            val entryTitle = entryTitleInput.text.toString()
            val entryInfo = entryInfoInput.text.toString()

            if (entryTitle.isNotBlank()) {
                val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val session = EntryEntity().apply {
                    this.entryTitle = entryTitle
                    this.entryInfo = entryInfo
                    this.date = date
                }

                Executors.newSingleThreadExecutor().execute {
                    db.exerciseDao().insert(session)
                    finish()
                }
            }
        }
    }
}