package com.thunderstorm.app.android.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.thunderstorm.app.CityStore
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.getIconForNameAndCode
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.database.datastore.SharedContext
import com.thunderstorm.app.model.weather.WeatherDataResult
import com.thunderstorm.app.networking.NetworkingClient
import kotlinx.coroutines.*

class WeatherViewModel : ViewModel() {

    var currentCity by mutableStateOf<CityStore?>(null)

    var forecastWeatherData by mutableStateOf<WeatherDataResult?>(null)
    var currentIconResource by mutableStateOf(R.drawable.ic_cloudy_night)

    var weatherAlertAvailable by mutableStateOf(false)
    var showWeatherData by mutableStateOf(false)

    private val asyncScope = CoroutineScope(Dispatchers.Default)

    fun loadDefaultCity(context: Context) {
        DataStore(context.applicationContext as SharedContext).apply {
            getCurrentData(context, getString("LAST_CITY_VIEWED"))
        }
    }

    fun getCurrentData(context: Context, serviceUrl: String) {
        val weatherAPIClient = NetworkingClient()
        asyncScope.launch {
            delay(500L)
            weatherAPIClient.getWeatherDataForCity(serviceUrl) { weatherResponse ->
                currentIconResource = context.getIconForNameAndCode(
                    weatherResponse.current.isDay,
                    weatherResponse.current.condition.code
                )
                forecastWeatherData = weatherResponse
                weatherAlertAvailable = weatherResponse.alerts.alert!!.isNotEmpty()
                currentCity = CityStore(
                    cityID = 0,
                    cityName = weatherResponse.location.name.split(",")[0],
                    stateName = weatherResponse.location.region,
                    countryName = weatherResponse.location.country,
                    serviceUrl = serviceUrl
                )
                showWeatherData = true
            }
        }
        DataStore(context.applicationContext as SharedContext).apply {
            putString("LAST_CITY_VIEWED", serviceUrl)
        }
    }

}