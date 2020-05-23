package com.example.weatherapp.location

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weatherapp.data.Repository
import com.example.weatherapp.mappers.mapWeatherResponse
import com.example.weatherapp.model.presentation.CurrentWeatherViewData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LocationViewModel : ViewModel() {
    private val repository = Repository()

    private val compositeDisposable = CompositeDisposable()

    val result = MutableLiveData<CurrentWeatherViewData>()
    val error = MutableLiveData<String>()
    val isLoading = MutableLiveData<Boolean>()

    fun getWeather(latitude: Double, longitude: Double) {
        compositeDisposable.add(
            repository.getWeather(latitude, longitude)
                .doFinally { isLoading.postValue(false) }
                .map { mapWeatherResponse(it) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { weather -> result.postValue(weather) },
                    { errorMessage -> error.postValue(errorMessage.message) }
                )
        )
    }
}
