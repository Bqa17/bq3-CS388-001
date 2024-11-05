package com.codepath.articlesearch

import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity(tableName = "diary_entries")
    data class EntryEntity(
        @PrimaryKey(autoGenerate = true) var id: Int = 0,
        var entryTitle: String = "",
        var entryInfo: String = "",
        var date: String = ""
    )
