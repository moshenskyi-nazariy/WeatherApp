package com.example.weatherapp.model.presentation

data class CurrentWeatherViewData(
    override val humidity: Int,
    override val pressure: Int,
    override val temperature: Int,
    val icon: String
) : WeatherViewData