package com.andriivanov.weatherapp.data.model

data class HourlyItemMapped(
    val data: List<HourlyData>
)

data class HourlyData(
    val temperature_2m: Double,
    val time: String,
    val weathercode: Int,
    val windspeed_10m: Double
)
