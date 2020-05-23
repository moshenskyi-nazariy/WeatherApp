package com.example.weatherapp.model.data

import com.google.gson.annotations.SerializedName

data class Hourly (

	@SerializedName("dt") val dateTime : Int,
	@SerializedName("temp") val temp : Double,
	@SerializedName("feels_like") val feelsLike : Double,
	@SerializedName("pressure") val pressure : Int,
	@SerializedName("humidity") val humidity : Int,
	@SerializedName("dew_point") val dewPoint : Double,
	@SerializedName("clouds") val clouds : Int,
	@SerializedName("wind_speed") val windSpeed : Double,
	@SerializedName("wind_deg") val windDeg : Int,
	@SerializedName("weather") val weather : List<Weather>,
	@SerializedName("rain") val rain : Rain
)