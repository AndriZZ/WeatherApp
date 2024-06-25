package com.andriivanov.weatherapp.utils

import com.andriivanov.weatherapp.data.model.ForecastResponse
import com.andriivanov.weatherapp.data.model.HourlyData
import com.andriivanov.weatherapp.data.model.HourlyItemMapped
import com.andriivanov.weatherapp.data.model.ForecastResponseModified

object WeatherResponseUtil {

    fun mapToHourly(
        temperatureList: List<Double>,
        timeList: List<String>,
        weatherCodeList: List<Int>,
        windSpeedList: List<Double>
    ): HourlyItemMapped {
        val data = temperatureList.mapIndexed { index, temperature ->
            HourlyData(
                temperature_2m = temperature,
                time = timeList[index],
                weathercode = weatherCodeList[index],
                windspeed_10m = windSpeedList[index]
            )
        }
        return HourlyItemMapped(data)
    }

    fun mapResponse(data: ForecastResponse): ForecastResponseModified {
        return ForecastResponseModified(
            elevation = data.elevation,
            generationtime_ms = data.generationtime_ms,
            hourlyItemMapped = mapToHourly(
                data.hourly.temperature_2m,
                data.hourly.time,
                data.hourly.weathercode,
                data.hourly.windspeed_10m
            ),
            hourly_units = data.hourly_units,
            latitude = data.latitude,
            longitude = data.longitude,
            timezone = data.timezone,
            timezone_abbreviation = data.timezone_abbreviation,
            utc_offset_seconds = data.utc_offset_seconds
        )
    }
}