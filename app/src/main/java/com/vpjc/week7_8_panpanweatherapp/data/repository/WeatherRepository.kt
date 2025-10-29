package com.vpjc.week7_8_panpanweatherapp.data.repository

import com.vpjc.week7_8_panpanweatherapp.data.service.WeatherAPIService
import com.vpjc.week7_8_panpanweatherapp.ui.model.Weather

data class WeatherIcon(val url: String)

class WeatherRepository(private val service: WeatherAPIService) {

    suspend fun getWeather(cityName: String): Weather {
        val weathers = service.getWeather(
            city = cityName,
            units = "metric",
            apiKey = "c41d64b799325960010c4b23acfa4a86"
        ).body()!!
        return Weather(
            cityName = weathers.name,
            dateTime = weathers.dt,

            weatherIconCode = weathers.weather[0].icon,
            weatherCondition = weathers.weather[0].main,
            temperature = weathers.main.temp,

            humidity = weathers.main.humidity,
            windSpeed = weathers.wind.speed,
            feelsLike = weathers.main.feels_like,
            rainFallLastHour = weathers.rain?.`1h`,
            pressure = weathers.main.pressure,
            cloudsAll = weathers.clouds.all,

            sunriseTime = weathers.sys.sunrise,
            sunsetTime = weathers.sys.sunset,

            isError = false,
            errorMessage = null
        )
    }

    fun getWeatherIcon(iconId: String): WeatherIcon {
        return WeatherIcon("https://openweathermap.org/img/wn/$iconId@2x.png")
    }
}
