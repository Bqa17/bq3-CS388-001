package com.codepath.articlesearch

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

    @Dao
    interface EntryDao {
        @Query("SELECT * FROM diary_entries")
        fun getAll(): Flow<List<EntryEntity>>

        @Insert
        fun insert(item: EntryEntity)

        @Insert
        fun insertAll(item: List<EntryEntity>)

        @Query("DELETE FROM diary_entries")
        fun deleteAll()
    }