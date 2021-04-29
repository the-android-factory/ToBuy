package com.dmp.tobuy

import android.content.Context
import android.content.SharedPreferences

object SharedPrefUtil {

    private const val PREF_PRIORITY_HIGH = "pref_priority_high"
    private const val PREF_PRIORITY_MEDIUM = "pref_priority_medium"
    private const val PREF_PRIORITY_LOW = "pref_priority_low"

    private const val DEFAULT_PRIORITY_HIGH = -3342336
    private const val DEFAULT_PRIORITY_MEDIUM = -3315456
    private const val DEFAULT_PRIORITY_LOW = -10107038

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(
            "${BuildConfig.APPLICATION_ID}.shared_preferences", Context.MODE_PRIVATE)
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
    
    fun getHighPriorityColor(): Int = getInt(PREF_PRIORITY_HIGH, DEFAULT_PRIORITY_HIGH)
    fun getMediumPriorityColor(): Int = getInt(PREF_PRIORITY_MEDIUM, DEFAULT_PRIORITY_MEDIUM)
    fun getLowPriorityColor(): Int = getInt(PREF_PRIORITY_LOW, DEFAULT_PRIORITY_LOW)
    
    fun setHighPriorityColor(color: Int) = setInt(PREF_PRIORITY_HIGH, color)
    fun setMediumPriorityColor(color: Int) = setInt(PREF_PRIORITY_MEDIUM, color)
    fun setLowPriorityColor(color: Int) = setInt(PREF_PRIORITY_LOW, color)

    private fun setInt(name: String, value: Int) {
        sharedPreferences.edit().putInt(name, value).apply()
    }

    private fun getInt(name: String, defaultValue: Int = 0): Int {
        return sharedPreferences.getInt(name, defaultValue)
    }
}