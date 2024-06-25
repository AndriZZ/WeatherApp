package com.andriivanov.weatherapp.data.repository

import com.andriivanov.weatherapp.data.model.ForecastResponseModified
import com.andriivanov.weatherapp.data.network.WeatherApi
import com.andriivanov.weatherapp.utils.Coordinates
import com.andriivanov.weatherapp.utils.Result
import com.andriivanov.weatherapp.utils.Result.*
import com.andriivanov.weatherapp.utils.WeatherResponseUtil.mapResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

open class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    open fun getWeatherForecast(destination: Coordinates): Flow<Result<ForecastResponseModified>> = flow {
        emit(Loading)
        try {
            val response = weatherApi.getWeatherForecast(
                latitude = destination.latitude,
                longitude = destination.longitude,
                hourly = "temperature_2m,weathercode,windspeed_10m"
            )
            emit(Success(mapResponse(response)))
        } catch (exception: HttpException) {
            emit(Error(exception.message.orEmpty()))
        } catch (exception: IOException) {
            emit(Error("Please check your network connection and try again!"))
        }
    }.flowOn(dispatcher)
}
