package com.example.weatherapp.model.presentation

data class CurrentWeatherViewData(
    val humidity: Int,
    val pressure: Int,
    val temperature: Double,
    val icon: String
)