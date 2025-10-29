package com.vpjc.week7_8_panpanweatherapp.ui.model

data class Weather(
    val cityName: String = "",
    val dateTime: Int = 0,
    val weatherIconCode: String = "",
    val temperature: Double = 0.0,
    val weatherCondition: String = "",
    val humidity: Int? = null,
    val windSpeed: Double? = null,
    val feelsLike: Double? = null,
    val rainFallLastHour: Double? = null,
    val pressure: Int? = null,
    val cloudsAll: Int? = null,
    val sunriseTime: Int = 0,
    val sunsetTime: Int = 0,
    val isError: Boolean = false,
    val errorMessage: String? = null
)