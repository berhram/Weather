package com.velvet.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.velvet.data.entity.City

@Database(entities = [City::class], version = 1)
@TypeConverters(Converters::class)
abstract class CityDatabase : RoomDatabase() { abstract fun cityDao(): CityDao }
