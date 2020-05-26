package com.example.weatherapp.location

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.R
import com.example.weatherapp.custom.CurrentWeatherView
import com.example.weatherapp.forecast.ForecastFragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationFragment : Fragment() {
    private var latitude: Double? = null
    private var longitude: Double? = null

    private lateinit var cityNameTextView: TextView
    private lateinit var currentWeatherView: CurrentWeatherView
    private lateinit var loadingBar: ProgressBar
    private lateinit var nextButton: Button

    private lateinit var viewModel: LocationViewModel
    private var fusedLocationClient: FusedLocationProviderClient? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.location_fragment, container, false)
        initUi(root)

        longitude = savedInstanceState?.getDouble(LONGITUDE_KEY)
        latitude = savedInstanceState?.getDouble(LATITUDE_KEY)

        return root
    }

    private fun initUi(root: View) {
        cityNameTextView = root.findViewById(R.id.city_name_tv)
        currentWeatherView = root.findViewById(R.id.current_weather_view)

        loadingBar = root.findViewById(R.id.loading_bar)
        loadingBar.visibility = VISIBLE

        nextButton = root.findViewById(R.id.weekly_forecast)
        nextButton.setOnClickListener {
            navigateToNextFragment()
        }
    }

    private fun navigateToNextFragment() {
        if (latitude != null && longitude != null) {
            activity?.supportFragmentManager
                ?.beginTransaction()
                ?.replace(
                    R.id.container,
                    ForecastFragment.newInstance(
                        latitude!!,
                        longitude!!,
                        cityNameTextView.text.toString()
                    )
                )?.commit()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViewModel()

        if (latitude == null && longitude == null) {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)

            if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                && checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
            ) {
                requestLocationPermissions()
            } else {
                lookupLocation()
            }
        } else {
            viewModel.getWeather(latitude!!, longitude!!)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (latitude != null && longitude != null) {
            outState.putDouble(LATITUDE_KEY, latitude!!)
            outState.putDouble(LONGITUDE_KEY, longitude!!)
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(LocationViewModel::class.java)
        viewModel.error.observe(viewLifecycleOwner, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_LONG).show()
        })
        viewModel.result.observe(viewLifecycleOwner, Observer {
            currentWeatherView.setWeather(it)
        })
        viewModel.isLoading.observe(viewLifecycleOwner, Observer { isLoading ->
            if (isLoading) {
                loadingBar.visibility = VISIBLE
            } else {
                loadingBar.visibility = GONE
            }
        })
    }

    private fun requestLocationPermissions() {
        requestPermissions(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            LOCATION_REQUEST_CODE
        )
    }

    private fun checkPermission(permission: String): Boolean {
        return checkSelfPermission(
            activity!!,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                lookupLocation()
            } else {
                Toast.makeText(activity!!, "Permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun lookupLocation() {
        fusedLocationClient!!.lastLocation
            .addOnSuccessListener(activity!!, this::onLocationUpdateSuccess)
            .addOnFailureListener(this::onLocationUpdateFailure)
    }

    private fun onLocationUpdateFailure(exception: Exception) {
        loadingBar.visibility = GONE

        activity?.let { context ->
            Toast.makeText(context, exception.message, Toast.LENGTH_LONG).show()
        }
    }

    private fun onLocationUpdateSuccess(location: Location) {
        latitude = location.latitude
        longitude = location.longitude

        viewModel.getWeather(location.latitude, location.longitude)

        val cityList = Geocoder(activity).getFromLocation(location.latitude, location.longitude, 1)
        if (cityList.isNotEmpty()) {
            cityNameTextView.text = cityList[0].locality
        }
    }

    companion object {
        const val LOCATION_REQUEST_CODE = 1000

        const val LATITUDE_KEY = "LATITUDE"
        const val LONGITUDE_KEY = "LONGITUDE"

        fun newInstance() = LocationFragment()
    }

}
