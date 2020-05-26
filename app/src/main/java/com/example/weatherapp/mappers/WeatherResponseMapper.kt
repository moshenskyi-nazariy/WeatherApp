package com.example.weatherapp.mappers

import com.example.weatherapp.model.data.WeatherResponse
import com.example.weatherapp.model.presentation.CurrentWeatherViewData
import com.example.weatherapp.model.presentation.DailyViewData
import java.text.SimpleDateFormat
import java.util.*

fun mapWeatherResponseToCurrent(weatherResponse: WeatherResponse): CurrentWeatherViewData {
    val currentWeather = weatherResponse.current

    return CurrentWeatherViewData(
        currentWeather.humidity,
        currentWeather.pressure,
        currentWeather.temperature.toInt(),
        "http://openweathermap.org/img/wn/${currentWeather.weather[0].icon}@2x.png"
    )
}

fun mapWeatherResponseToDailyList(weatherResponse: WeatherResponse): List<DailyViewData> {
    val dailyList = mutableListOf<DailyViewData>()

    for (daily in weatherResponse.daily) {
        dailyList.add(
            DailyViewData(
                daily.humidity,
                daily.pressure,
                daily.temperature.day.toInt(),
                mapDayOfWeek(daily.dateTime)
            )
        )
    }
    return dailyList
}

private fun mapDayOfWeek(dateTime: Int): String {
    val unixTimeMillis = dateTime.toLong() * MILLIS_IN_SECOND
    return SimpleDateFormat("E", Locale.ENGLISH).format(unixTimeMillis)
}

private const val MILLIS_IN_SECOND = 1000
