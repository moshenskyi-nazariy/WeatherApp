package com.example.weatherapp.forecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.Repository
import com.example.weatherapp.mappers.mapWeatherResponseToCurrent
import com.example.weatherapp.mappers.mapWeatherResponseToDailyList
import com.example.weatherapp.model.presentation.CurrentWeatherViewData
import com.example.weatherapp.model.presentation.DailyViewData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ForecastViewModel : ViewModel() {
    private val repository = Repository()

    private val compositeDisposable = CompositeDisposable()

    val result = MutableLiveData<List<DailyViewData>>()
    val currentWeather = MutableLiveData<CurrentWeatherViewData>()
    val error = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    fun getForecast(latitude: Double, longitude: Double) {
        compositeDisposable.add(
            repository.getWeather(latitude, longitude)
                .doFinally { isLoading.postValue(false) }
                .doOnSuccess { currentWeather.postValue(mapWeatherResponseToCurrent(it)) }
                .map { mapWeatherResponseToDailyList(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { weather -> result.postValue(weather) },
                    { errorMessage -> error.postValue(errorMessage.message) }
                )
        )
    }

    override fun onCleared() {
        compositeDisposable.clear()
    }
}
