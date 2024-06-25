package com.andriivanov.weatherapp.utils

object Destinations {
    val SOFIA = Coordinates(latitude = 42.70, longitude = 23.32)
    val PLOVDIV = Coordinates(latitude = 42.15, longitude = 24.75)
    val BURGAS = Coordinates(latitude = 42.51, longitude = 27.47)

    fun getDestination(index: Int): Coordinates {
        return when (index) {
            1 -> SOFIA
            2 -> PLOVDIV
            3 -> BURGAS
            else -> {
                SOFIA
            }
        }
    }
    fun getDestinationName(index: Int): String {
        return when (index) {
            0 -> "Sofia"
            1 -> "Burgas"
            2 -> "Plovdiv"
            else -> "Unknown"
        }
    }
}

data class Coordinates(val latitude: Double, val longitude: Double)
