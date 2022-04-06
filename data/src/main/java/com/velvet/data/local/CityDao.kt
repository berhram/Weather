package com.velvet.data.local

import androidx.room.*
import com.velvet.data.entity.City

@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    fun getAll(): List<City>

    @Insert
    fun insertAll(cards: List<City>)

    @Delete
    fun deleteAll(cards: List<City>)
}