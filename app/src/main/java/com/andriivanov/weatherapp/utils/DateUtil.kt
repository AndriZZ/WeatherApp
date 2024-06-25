package com.andriivanov.weatherapp.utils

import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale

object DateUtil {
    fun String.toFormattedDate(): String {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("MMM d", Locale.getDefault())

        try {
            val date = inputDateFormat.parse(this)
            if (date != null) {
                return outputDateFormat.format(date)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
        return this
    }

    fun String.toFormattedDay(): String {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("MMM d", Locale.getDefault())

        try {
            val date = inputDateFormat.parse(this)
            if (date != null) {
                return outputDateFormat.format(date)
            }
        } catch (e: Exception) {
            Timber.e(e)
        }
        return this
    }
}
