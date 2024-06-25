package com.andriivanov.weatherapp.data.model

data class HourlyResponse(
    val temperature_2m: List<Double>,
    val time: List<String>,
    val weathercode: List<Int>,
    val windspeed_10m: List<Double>
)
