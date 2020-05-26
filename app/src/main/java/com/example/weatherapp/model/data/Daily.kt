package com.example.weatherapp.model.data

import com.google.gson.annotations.SerializedName

data class Daily (
	@SerializedName("dt") val dateTime : Int,
	@SerializedName("sunrise") val sunrise : Int,
	@SerializedName("sunset") val sunset : Int,
	@SerializedName("temp") val temperature : Temperature,
	@SerializedName("feels_like") val feelsLike : FeelsLike,
	@SerializedName("pressure") val pressure : Int,
	@SerializedName("humidity") val humidity : Int,
	@SerializedName("dew_point") val dewPoint : Double,
	@SerializedName("wind_speed") val windSpeed : Double,
	@SerializedName("wind_deg") val windDeg : Int,
	@SerializedName("weather") val weather : List<Weather>,
	@SerializedName("clouds") val clouds : Int,
	@SerializedName("rain") val rain : Double,
	@SerializedName("uvi") val uvi : Double
)