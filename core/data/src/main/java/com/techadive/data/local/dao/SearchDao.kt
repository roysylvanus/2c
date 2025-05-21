package com.techadive.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.techadive.data.local.entities.KeywordEntity

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecentSearch(keywordEntity: KeywordEntity)

    @Query("SELECT * FROM search_keywords")
    suspend fun getRecentSearch(): List<KeywordEntity>?

    @Query("DELETE FROM search_keywords")
    suspend fun deleteAllRecentSearchHistory()

    @Query("SELECT * FROM search_keywords WHERE keyword = :query LIMIT 1")
    suspend fun getRecentSearchByQuery(query: String): KeywordEntity?

    @Update
    suspend fun update(keywordEntity: KeywordEntity)

    @Transaction
    suspend fun addOrUpdateRecentSearch(keywordEntity: KeywordEntity) {
        val existingItem = getRecentSearchByQuery(keywordEntity.keyword)
        if (existingItem != null) {
            update(existingItem.copy(timeStamp = existingItem.timeStamp))
        } else {
            insertRecentSearch(keywordEntity)
        }
    }
}