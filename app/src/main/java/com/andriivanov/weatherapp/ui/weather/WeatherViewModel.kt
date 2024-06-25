package com.andriivanov.weatherapp.ui.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andriivanov.weatherapp.data.repository.WeatherRepository
import com.andriivanov.weatherapp.utils.Coordinates
import com.andriivanov.weatherapp.utils.Destinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<WeatherUiState> =
        MutableStateFlow(WeatherUiState(isLoading = true))
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    fun getWeather(city: Coordinates = Destinations.SOFIA) {
        repository.getWeatherForecast(city).map { result ->
            when (result) {
                is com.andriivanov.weatherapp.utils.Result.Success -> {
                    _uiState.value = WeatherUiState(weather = result.data)
                }

                is com.andriivanov.weatherapp.utils.Result.Error-> {
                    _uiState.value = WeatherUiState(errorMessage = result.errorMessage)
                }

                com.andriivanov.weatherapp.utils.Result.Loading -> {
                    _uiState.value = WeatherUiState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}