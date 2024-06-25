package com.andriivanov.weatherapp.utils

import com.andriivanov.weatherapp.R


object WeatherUtil {
    fun getWeatherIcon(weatherCode: Int): Int {
        return when (weatherCode) {
            in 0..2 -> R.drawable.sunny
            in 3..51 -> R.drawable.cloudy
            in 52..99 -> R.drawable.rainy
            else -> R.drawable.sunny
        }
    }
}