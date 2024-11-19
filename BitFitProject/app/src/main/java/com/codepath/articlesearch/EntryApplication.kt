package com.codepath.articlesearch

import android.app.Application

class EntryApplication : Application() {
    val db by lazy { AppDatabase.getDatabase(this) }
}