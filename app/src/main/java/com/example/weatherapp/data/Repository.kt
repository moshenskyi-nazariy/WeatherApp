package com.example.weatherapp.data

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.model.data.WeatherResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Repository : IRepository {

    private val api: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .client(getClient())
            .build()

        api = retrofit.create(ApiService::class.java)
    }

    private fun getClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    override fun getWeather(lat: Double, lon: Double): Single<WeatherResponse> {
        return api.getWeather(lat, lon, BuildConfig.API_KEY)
    }
}