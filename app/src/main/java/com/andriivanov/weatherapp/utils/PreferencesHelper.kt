package com.andriivanov.weatherapp.utils

import android.content.Context
import android.content.SharedPreferences

 class PreferencesHelper(context: Context):PreferenceHelper {

     companion object {
         const val SELECTED_TAB_INDEX = "selected_index"
         private const val PREFERENCES_NAME = "MyAppPreferences"
     }

     private val sharedPreferences: SharedPreferences =
         context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

     override fun saveString(key: String, value: String) {
         with(sharedPreferences.edit()) {
             putString(key, value)
             apply()
         }
     }

     override fun saveInt(key: String, value: Int) {
         with(sharedPreferences.edit()) {
             putInt(key, value)
             apply()
         }
     }

     override fun getString(key: String, defaultValue: String): String {
         return sharedPreferences.getString(key, defaultValue) ?: defaultValue
     }

     override fun getInt(key: String, defaultValue: Int): Int {
         return sharedPreferences.getInt(key, defaultValue)
     }
}

// Define PreferencesHelper interface
interface PreferenceHelper {
    fun saveString(key: String, value: String)
    fun saveInt(key: String, value: Int)
    fun getString(key: String, defaultValue: String = ""): String
    fun getInt(key: String, defaultValue: Int = 0): Int
}

