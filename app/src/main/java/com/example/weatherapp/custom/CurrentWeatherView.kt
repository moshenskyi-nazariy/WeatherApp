package com.example.weatherapp.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.model.presentation.CurrentWeatherViewData

class CurrentWeatherView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private val temperature: TextView
    private val humidity: TextView
    private val pressure: TextView

    private val weatherIcon: ImageView

    init {
        orientation = VERTICAL

        LayoutInflater.from(context).inflate(R.layout.current_weather_view, this)

        temperature = findViewById(R.id.temperature_tv)
        humidity = findViewById(R.id.humidity_tv)
        pressure = findViewById(R.id.pressure_tv)
        weatherIcon = findViewById(R.id.weather_icon)
    }

    fun setWeather(currentWeather: CurrentWeatherViewData) {
        temperature.text = resources.getString(R.string.temperature_template, currentWeather.temperature)
        humidity.text = resources.getString(R.string.humidity_template, currentWeather.humidity)
        pressure.text = resources.getString(R.string.pressure_template, currentWeather.pressure)

        loadIcon(currentWeather.icon)
    }

    private fun loadIcon(url: String) {
        Glide.with(weatherIcon)
            .load(url)
            .centerCrop()
            .into(weatherIcon)
    }

}