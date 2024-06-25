package com.andriivanov.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.andriivanov.weatherapp.ui.theme.WeatherTheme
import com.andriivanov.weatherapp.ui.weather.WeatherScreen
import com.andriivanov.weatherapp.utils.PreferencesHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferencesHelper: PreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherTheme {
                WeatherScreen(preferencesHelper = preferencesHelper)
            }
        }
    }
}
