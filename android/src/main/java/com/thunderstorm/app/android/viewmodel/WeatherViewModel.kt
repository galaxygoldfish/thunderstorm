package com.thunderstorm.app.android.viewmodel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

    val currentCityName: MutableLiveData<String> = MutableLiveData()
    val currentRegionName: MutableLiveData<String> = MutableLiveData()
    val currentCityServiceUrl: MutableLiveData<String> = MutableLiveData()

    val forecastWeatherData: MutableState<WeatherDataResult?> = mutableStateOf(null)
    val currentIconResource: MutableState<Int> = mutableStateOf(R.drawable.ic_cloudy_night)

    val weatherAlertAvailable: MutableState<Boolean> = mutableStateOf(false)
    val showWeatherData: MutableState<Boolean> = mutableStateOf(false)

    private val asyncScope = CoroutineScope(Dispatchers.IO + Job())
    private val mainScope = CoroutineScope(Dispatchers.Main + Job())

    fun loadDefaultCity(context: Context) {
        asyncScope.launch {
            val database = ThunderstormDatabase(DatabaseDriver(context).createDriver())
            val cityName = database.cityStoreQueries.getAllCities().executeAsList()[0]
            mainScope.launch {
                currentCityName.value = cityName.cityName.split(",")[0]
                currentRegionName.value = cityName.stateName
                currentCityServiceUrl.value = cityName.serviceUrl
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
                    delay(550L)
                    showWeatherData.value = true
                    weatherAlertAvailable.value = weatherResponse.alerts.alert!!.isNotEmpty()
                }
            }
        } catch (exception: Exception) {

        }
    }

}