package com.example.weatherapp.model.presentation

data class DailyViewData(override val humidity: Int,
                         override val pressure: Int,
                         override val temperature: Int,
                         val dayOfWeek: String
): WeatherViewData