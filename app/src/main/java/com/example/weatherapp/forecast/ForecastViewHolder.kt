package com.example.weatherapp.forecast

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.custom.CurrentWeatherView
import com.example.weatherapp.model.presentation.DailyViewData

class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val weatherView: CurrentWeatherView = itemView.findViewById(R.id.weather_item)

    fun bind(currentWeatherView: DailyViewData) {
        weatherView.setWeather(currentWeatherView)
    }
}