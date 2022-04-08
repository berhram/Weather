package com.velvet.data.local

import androidx.room.*
import com.velvet.data.entity.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface CityDao {
    @Query("SELECT * FROM city")
    fun getAll(): Flow<List<City>>

    fun getAllDistinctUntilChanged() = getAll().distinctUntilChanged()

    @Query("SELECT * FROM city WHERE id LIKE :targetId LIMIT 1")
    fun getById(targetId: String): City

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cities: List<City>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cities: City)

    @Delete
    fun deleteAll(cities: List<City>)

    @Delete
    fun delete(city: City)
}