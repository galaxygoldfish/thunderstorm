package com.thunderstorm.app.android.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.thunderstorm.app.model.weather.special.AlertWeatherObject
import com.thunderstorm.app.networking.NetworkingClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class WeatherAlertViewModel : ViewModel() {

    val weatherAlertList: MutableState<List<AlertWeatherObject?>> = mutableStateOf(mutableListOf())
    val currentCity: MutableState<String> = mutableStateOf("")

    fun fetchAllAlerts(cityToFetch: String) {
        currentCity.value = cityToFetch
        val weatherAPIClient = NetworkingClient()
        val asyncScope = CoroutineScope(Dispatchers.IO)
        asyncScope.launch {
            weatherAPIClient.getWeatherDataForCity(cityToFetch) { alertResult ->
                weatherAlertList.value = alertResult.alerts.alert!!
            }
        }
    }

}