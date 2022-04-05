package com.velvet.data.local

import androidx.room.*

@Dao
interface CardDao {
    @Query("SELECT * FROM card WHERE name LIKE :cardName LIMIT 1")
    fun findByName(cardName: String): Card

    @Query("SELECT * FROM card")
    fun getAll(): List<Card>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cards: List<Card>)

    @Delete
    fun deleteAll(cards: List<Card>)
}