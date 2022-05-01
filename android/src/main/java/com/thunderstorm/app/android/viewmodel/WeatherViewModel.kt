package com.thunderstorm.app.android.viewmodel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.thunderstorm.app.ThunderstormDatabase
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.utils.getIconForNameAndCode
import com.thunderstorm.app.database.DatabaseDriver
import com.thunderstorm.app.model.weather.WeatherDataResult
import com.thunderstorm.app.networking.NetworkingClient
import kotlinx.coroutines.*

class WeatherViewModel : ViewModel() {

    var currentCityName by mutableStateOf<String?>(null)
    var currentRegionName by mutableStateOf<String?>(null)
    var currentCityServiceUrl by mutableStateOf<String?>(null)

    val forecastWeatherData: MutableState<WeatherDataResult?> = mutableStateOf(null)
    val currentIconResource: MutableState<Int> = mutableStateOf(R.drawable.ic_cloudy_night)

    val weatherAlertAvailable: MutableState<Boolean> = mutableStateOf(false)
    val showWeatherData: MutableState<Boolean> = mutableStateOf(false)

    private val asyncScope = CoroutineScope(Dispatchers.Default)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    fun loadDefaultCity(context: Context) {
        asyncScope.launch {
            val database = ThunderstormDatabase(DatabaseDriver(context).createDriver())
            val cityName = database.cityStoreQueries.getAllCities().executeAsList()[0]
            mainScope.launch {
                currentCityName = cityName.cityName.split(",")[0]
                currentRegionName = cityName.stateName
                currentCityServiceUrl = cityName.serviceUrl
            }
        }
    }

    fun getCurrentData(context: Context, cityNameJson: String) {
        val weatherAPIClient = NetworkingClient()
        try {
            asyncScope.launch {
                val weatherResponse = weatherAPIClient.getWeatherDataForCity(cityNameJson)
                mainScope.launch {
                    currentIconResource.value = context.getIconForNameAndCode(
                        weatherResponse.current.isDay,
                        weatherResponse.current.condition.code
                    )
                    forecastWeatherData.value = weatherResponse
                    showWeatherData.value = true
                    weatherAlertAvailable.value = weatherResponse.alerts.alert!!.isNotEmpty()
                }
            }
        } catch (_: Exception) {

        }
    }

}