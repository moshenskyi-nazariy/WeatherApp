package com.example.weatherapp.forecast

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.custom.CurrentWeatherView
import com.example.weatherapp.location.LocationFragment.Companion.LATITUDE_KEY
import com.example.weatherapp.location.LocationFragment.Companion.LONGITUDE_KEY

class ForecastFragment : Fragment() {

    private lateinit var viewModel: ForecastViewModel

    private lateinit var cityNameTextView: TextView
    private lateinit var currentWeatherView: CurrentWeatherView
    private lateinit var loadingBar: ProgressBar
    private lateinit var weatherForecast: RecyclerView
    private lateinit var adapter: ForecastAdapter

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0
    private var cityName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            latitude = it.getDouble(LATITUDE_KEY)
            longitude = it.getDouble(LONGITUDE_KEY)
            cityName = it.getString(CITY_NAME_KEY, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.forecast_fragment, container, false)
        initUi(root)

        return root
    }

    private fun initUi(root: View) {
        cityNameTextView = root.findViewById(R.id.city_name_tv)
        cityNameTextView.text = cityName

        currentWeatherView = root.findViewById(R.id.current_weather_view)

        loadingBar = root.findViewById(R.id.loading_bar)
        loadingBar.visibility = View.VISIBLE

        weatherForecast = root.findViewById(R.id.weather_forecast)
        weatherForecast.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        adapter = ForecastAdapter()
        weatherForecast.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForecastViewModel::class.java)
        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        viewModel.result.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
        viewModel.currentWeather.observe(viewLifecycleOwner, Observer {
            currentWeatherView.setWeather(it)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                loadingBar.visibility = View.VISIBLE
            } else {
                loadingBar.visibility = View.GONE
            }
        })
        viewModel.getForecast(latitude, longitude)
    }

    companion object {
        private const val CITY_NAME_KEY = "CITY_NAME"

        fun newInstance(latitude: Double, longitude: Double, cityName: String) =
            ForecastFragment().apply {
                val bundle = Bundle()
                bundle.putDouble(LATITUDE_KEY, latitude)
                bundle.putDouble(LONGITUDE_KEY, longitude)
                bundle.putString(CITY_NAME_KEY, cityName)
                arguments = bundle
            }
    }

}
