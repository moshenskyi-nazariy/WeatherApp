package com.example.weatherapp.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.model.presentation.CurrentWeatherViewData
import com.example.weatherapp.model.presentation.DailyViewData
import com.example.weatherapp.model.presentation.WeatherViewData

class CurrentWeatherView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr) {
    private val temperature: TextView
    private val humidity: TextView
    private val pressure: TextView
    private val dayOfWeek: TextView

    private val weatherIcon: ImageView

    private val weatherDataLayout: LinearLayout

    init {
        orientation = VERTICAL

        LayoutInflater.from(context).inflate(R.layout.current_weather_view, this)

        temperature = findViewById(R.id.temperature_tv)
        humidity = findViewById(R.id.humidity_tv)
        pressure = findViewById(R.id.pressure_tv)
        weatherIcon = findViewById(R.id.weather_icon)
        weatherDataLayout = findViewById(R.id.weather_data)
        dayOfWeek = findViewById(R.id.weekday_tv)
    }

    fun setWeather(currentWeather: WeatherViewData) {
        temperature.text =
            resources.getString(R.string.temperature_template, currentWeather.temperature)
        humidity.text = resources.getString(R.string.humidity_template, currentWeather.humidity)
        pressure.text = resources.getString(R.string.pressure_template, currentWeather.pressure)

        when (currentWeather) {
            is CurrentWeatherViewData -> {
                changeWeatherDataOrientation(HORIZONTAL)
                loadIcon(currentWeather.icon)
            }
            is DailyViewData -> {
                dayOfWeek.visibility = View.VISIBLE
                dayOfWeek.text = currentWeather.dayOfWeek
                changeWeatherDataOrientation(VERTICAL)
                weatherIcon.visibility = View.GONE
            }
        }
    }

    private fun changeWeatherDataOrientation(@OrientationMode orientation: Int) {
        weatherDataLayout.orientation = orientation
        weatherDataLayout.requestLayout()
    }

    private fun loadIcon(url: String) {
        Glide.with(weatherIcon)
            .load(url)
            .centerCrop()
            .into(weatherIcon)
    }

}