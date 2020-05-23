package com.example.weatherapp.model.data

import com.google.gson.annotations.SerializedName

data class Minutely (

	@SerializedName("dt") val dateTime : Int,
	@SerializedName("precipitation") val precipitation : Int
)