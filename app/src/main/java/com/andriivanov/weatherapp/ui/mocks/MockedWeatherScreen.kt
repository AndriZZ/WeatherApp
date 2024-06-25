package com.andriivanov.weatherapp.ui.mocks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.andriivanov.weatherapp.data.model.ForecastResponse
import com.andriivanov.weatherapp.data.model.HourlyData
import com.andriivanov.weatherapp.data.model.HourlyItemMapped
import com.andriivanov.weatherapp.data.model.HourlyResponse
import com.andriivanov.weatherapp.data.model.HourlyUnits
import com.andriivanov.weatherapp.data.model.ForecastResponseModified
import com.andriivanov.weatherapp.data.network.WeatherApi
import com.andriivanov.weatherapp.data.repository.WeatherRepository
import com.andriivanov.weatherapp.ui.weather.WeatherViewModel
import com.andriivanov.weatherapp.utils.Coordinates
import com.andriivanov.weatherapp.utils.PreferenceHelper
import com.andriivanov.weatherapp.utils.Result
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object MockedWeatherScreen {

    // Mock ViewModel for preview
    @Composable
    fun mockWeatherViewModel(): WeatherViewModel {
        val viewModel = WeatherViewModel(MockWeatherRepository())
        LaunchedEffect(Unit) {
            viewModel.getWeather()
        }
        return viewModel
    }

    // Mock implementation of WeatherApi for testing
    class MockWeatherApi : WeatherApi {
        override suspend fun getWeatherForecast(
            latitude: Double,
            longitude: Double,
            hourly: String
        ): ForecastResponse {
            // Simulate a delay to mimic network call
            kotlinx.coroutines.delay(1000)

            // Return mock data for testing
            return ForecastResponse(
                elevation = 100.0,
                generationtime_ms = 50.0,
                hourly = HourlyResponse(
                    temperature_2m = listOf(25.0, 26.0, 27.0, 28.0, 29.0),
                    time = listOf(
                        "2023-06-25T12:00:00Z",
                        "2023-06-25T13:00:00Z",
                        "2023-06-25T14:00:00Z",
                        "2023-06-25T15:00:00Z",
                        "2023-06-25T16:00:00Z"
                    ),
                    weathercode = listOf(1, 2, 3, 4, 5),
                    windspeed_10m = listOf(10.0, 12.0, 15.0, 8.0, 5.0)
                ),
                hourly_units = HourlyUnits(
                    temperature_2m = "Celsius",
                    windspeed_10m = "km/h",
                    time = "UTC",
                    weathercode = "0"
                ),
                latitude = latitude,
                longitude = longitude,
                timezone = "Europe/Sofia",
                timezone_abbreviation = "EET",
                utc_offset_seconds = 7200
            )
        }
    }
    // Mock implementation of PreferencesHelper for testing
    class MockPreferencesHelper : PreferenceHelper {

        private val preferencesMap = mutableMapOf<String, Any>()

        override fun saveString(key: String, value: String) {
            preferencesMap[key] = value
        }

        override fun saveInt(key: String, value: Int) {
            preferencesMap[key] = value
        }

        override fun getString(key: String, defaultValue: String): String {
            return preferencesMap[key] as? String ?: defaultValue
        }

        override fun getInt(key: String, defaultValue: Int): Int {
            return preferencesMap[key] as? Int ?: defaultValue
        }
    }
    // Mock implementation of WeatherRepository for testing
    class MockWeatherRepository : WeatherRepository(MockWeatherApi()) {
        override fun getWeatherForecast(destination: Coordinates): Flow<Result<ForecastResponseModified>> = flow {
            // Simulate emitting loading state
            emit(Result.Loading)

            // Simulate network delay or background work
            delay(1000)
            emit(
                Result.Success(
                ForecastResponseModified(
                    elevation = 100.0,
                    generationtime_ms = 50.0,
                    hourlyItemMapped = HourlyItemMapped(
                        data = listOf(
                            HourlyData(
                                time = "2023-06-25T14:00:00Z",
                                temperature_2m = 27.0,
                                windspeed_10m = 15.0,
                                weathercode = 3
                            )
                        )
                    ),
                    hourly_units = HourlyUnits(
                        temperature_2m = "Celsius",
                        windspeed_10m = "km/h",
                        time = "UTC",
                        weathercode = "0"
                    ),
                    latitude = 42.6977,
                    longitude = 23.3219,
                    timezone = "Europe/Sofia",
                    timezone_abbreviation = "EET",
                    utc_offset_seconds = 7200
                )
            )
            )
        }
    }
}