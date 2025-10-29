package com.vpjc.week7_8_panpanweatherapp.data.container

import com.vpjc.week7_8_panpanweatherapp.data.repository.WeatherRepository
import com.vpjc.week7_8_panpanweatherapp.data.service.WeatherAPIService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherContainer {
    companion object {
        val baseUrl = "https://api.openweathermap.org/"
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(baseUrl)
        .build()

    private val weatherService: WeatherAPIService by lazy {
        retrofit.create(WeatherAPIService::class.java)
    }

    val weatherRepository: WeatherRepository by lazy {
        WeatherRepository(weatherService)
    }

}