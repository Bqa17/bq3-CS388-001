package com.codepath.articlesearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.codepath.articlesearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val entryListFragment by lazy { EntryListFragment() }
    private val dashBoardFragment by lazy { DashboardFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_log -> replaceFragment(entryListFragment)
                R.id.nav_dashboard -> replaceFragment(dashBoardFragment)
            }
            true
        }

        binding.bottomNavigation.selectedItemId = R.id.nav_log
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.log_frame_layout, fragment)
            .commit()
    }
}
