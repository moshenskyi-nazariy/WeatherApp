package com.example.weatherapp.mappers

import com.example.weatherapp.model.data.WeatherResponse
import com.example.weatherapp.model.presentation.CurrentWeatherViewData

fun mapWeatherResponse(weatherResponse: WeatherResponse): CurrentWeatherViewData {
    val currentWeather = weatherResponse.current

    return CurrentWeatherViewData(
        currentWeather.humidity,
        currentWeather.pressure,
        currentWeather.temperature,
        "http://openweathermap.org/img/wn/${currentWeather.weather[0].icon}@2x.png"
    )
}