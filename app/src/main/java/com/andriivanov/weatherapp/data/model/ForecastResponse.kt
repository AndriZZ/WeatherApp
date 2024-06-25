package com.andriivanov.weatherapp.data.model



data class ForecastResponse(
    val elevation: Double,
    val generationtime_ms: Double,
    val hourly: HourlyResponse,
    val hourly_units: HourlyUnits,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezone_abbreviation: String,
    val utc_offset_seconds: Int
)

data class HourlyUnits(
    val temperature_2m: String,
    val time: String,
    val weathercode: String,
    val windspeed_10m: String
)

