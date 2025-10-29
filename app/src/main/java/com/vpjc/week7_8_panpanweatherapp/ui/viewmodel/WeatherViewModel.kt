package com.vpjc.week7_8_panpanweatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vpjc.week7_8_panpanweatherapp.R
import com.vpjc.week7_8_panpanweatherapp.data.container.WeatherContainer
import com.vpjc.week7_8_panpanweatherapp.ui.model.Weather
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherViewModel : ViewModel() {

    private val _weather = MutableStateFlow(Weather())

    val weather: StateFlow<Weather> = _weather

    private val _weatherIconUrl = MutableStateFlow<String?>(null)
    val weatherIconUrl: StateFlow<String?> = _weatherIconUrl

    val currentDate = weather.map {
        val date = Date(it.dateTime * 1000L)
        SimpleDateFormat("MMMM dd", Locale("id")).format(date)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val currentTime = weather.map {
        val date = Date(it.dateTime * 1000L)
        SimpleDateFormat("HH:mm a", Locale("id")).format(date)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val sunriseTime = weather.map {
        SimpleDateFormat("HH:mm a", Locale("id")).format(Date(it.sunriseTime * 1000L))
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val sunsetTime = weather.map {
        SimpleDateFormat("HH:mm a", Locale("id")).format(Date(it.sunsetTime * 1000L))
    }.stateIn(viewModelScope, SharingStarted.Eagerly, "")

    val listWeatherInfo = weather.map {
        listOf(
            Triple("HUMIDITY", "${it.humidity ?: 0}%", R.drawable.icon_humidity),
            Triple("WIND", "${it.windSpeed ?: 0}km/h", R.drawable.icon_wind),
            Triple("FEELS LIKE", "${it.feelsLike ?: 0}°", R.drawable.icon_feels_like),
            Triple("RAIN FALL", "${it.rainFallLastHour ?: 0} mm", R.drawable.vector_2),
            Triple("PRESSURE", "${it.pressure ?: 0}hPa", R.drawable.devices),
            Triple("CLOUDS", "${it.cloudsAll ?: 0}%", R.drawable.cloud)
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val listSunInfo = combine(sunriseTime, sunsetTime) { sunrise, sunset ->
        listOf(
            Triple("SUNRISE", sunrise, R.drawable.vector),
            Triple("SUNSET", sunset, R.drawable.vector_21png)
        )
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun loadWeather(cityName: String) {
        viewModelScope.launch {
            try {
                val result = WeatherContainer().weatherRepository.getWeather(cityName)

                _weather.value = result.copy(
                    isError = false,
                    errorMessage = null
                )

                _weatherIconUrl.value = WeatherContainer().weatherRepository.getWeatherIcon(
                    result.weatherIconCode
                ).url

            } catch (e: Exception) {
                _weather.value = _weather.value.copy(
                    isError = true,
                    errorMessage = "HTTP 404 Not Found"
                )
                _weatherIconUrl.value = null
            }
        }
    }
}

