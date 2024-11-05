package com.codepath.articlesearch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {
    private lateinit var cacheSwitch: Switch
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Set up the switch and load the current state from SharedPreferences
        cacheSwitch = findViewById(R.id.switch_cache)
        cacheSwitch.isChecked = sharedPreferences.getBoolean(PREF_CACHE_DATA, true)

        // Listen for toggle changes
        cacheSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean(PREF_CACHE_DATA, isChecked).apply()
        }
    }
}
