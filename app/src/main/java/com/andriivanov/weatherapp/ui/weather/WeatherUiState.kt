package com.andriivanov.weatherapp.ui.weather

import com.andriivanov.weatherapp.data.model.ForecastResponseModified

data class WeatherUiState(
    val weather: ForecastResponseModified? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
)
