package com.andriivanov.weatherapp.data.model


data class ForecastResponseModified(
    val elevation: Double,
    val generationtime_ms: Double,
    val hourlyItemMapped: HourlyItemMapped? = null,
    val hourly_units: HourlyUnits,
    val latitude: Double,
    val longitude: Double,
    val timezone: String,
    val timezone_abbreviation: String,
    val utc_offset_seconds: Int
)
