package com.andriivanov.weatherapp.data.model

data class DailyForecast(
    val date: String,
    val minTemp: Double,
    val maxTemp: Double,
    val weatherCode: Int
)