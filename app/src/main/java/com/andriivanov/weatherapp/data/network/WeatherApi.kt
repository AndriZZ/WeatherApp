package com.andriivanov.weatherapp.data.network

import com.andriivanov.weatherapp.data.model.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApi {
    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String,
    ): ForecastResponse
}