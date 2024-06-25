package com.andriivanov.weatherapp.di

import android.content.Context
import com.andriivanov.weatherapp.data.network.WeatherApi
import com.andriivanov.weatherapp.data.repository.WeatherRepository
import com.andriivanov.weatherapp.utils.PreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(weatherApi: WeatherApi): WeatherRepository =
        WeatherRepository(weatherApi)

    @Provides
    @Singleton
    fun providePreferencesHelper(@ApplicationContext context: Context): PreferencesHelper {
        return PreferencesHelper(context)
    }
}