package com.example.weatherapp.data

import com.example.weatherapp.model.data.WeatherResponse
import io.reactivex.Single

interface IRepository {
    fun getWeather(lat: Double, lon: Double): Single<WeatherResponse>
}