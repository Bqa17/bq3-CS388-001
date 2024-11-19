package com.codepath.articlesearch

import androidx.room.Dao
import androidx.room.Delete
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

        @Delete
        fun delete(edible: EntryEntity)

        @Query("DELETE FROM diary_entries WHERE id = :id")
        fun deleteById(id: Int)

        @Query("DELETE FROM diary_entries")
        fun deleteAll()

        @Query("SELECT COUNT(*) FROM diary_entries")
        fun getTotalEntries() : Int

        @Query("SELECT entryTitle FROM diary_entries ORDER BY id DESC LIMIT 1")
        fun getMostRecentEntryTitle() : String

        @Query("SELECT entryInfo FROM diary_entries ORDER BY id DESC LIMIT 1")
        fun getMostRecentEntryText() : String
    }
