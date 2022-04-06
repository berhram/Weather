package com.velvet.data.local

import android.content.Context
import android.content.SharedPreferences
import com.velvet.data.Settings.LAST_UPDATED_KEY
import com.velvet.data.Settings.SHARED_PREFS

class TimeStore(context: Context) {
    private val preferences: SharedPreferences = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    fun saveTime(time: Long) {
        preferences.edit().putLong(LAST_UPDATED_KEY, time).apply()
    }

    fun getTime() : Long {
        return preferences.getLong(LAST_UPDATED_KEY, 0)
    }
}