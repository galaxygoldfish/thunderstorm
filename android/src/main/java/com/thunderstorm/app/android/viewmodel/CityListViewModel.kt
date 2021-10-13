package com.thunderstorm.app.android.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.thunderstorm.app.ThunderstormDatabase
import com.thunderstorm.app.database.DatabaseDriver
import com.thunderstorm.app.model.SavedCityItem
import com.thunderstorm.app.networking.NetworkingClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CityListViewModel : ViewModel() {

    private val cityListInternal = mutableListOf<SavedCityItem>()
    val cityList: MutableState<MutableList<SavedCityItem>> = mutableStateOf(cityListInternal)

    fun loadSavedCities(context: Context) {
        val database = ThunderstormDatabase(DatabaseDriver(context).createDriver())
        val savedCities = database.cityStoreQueries.getAllCities()
        val asyncScope = CoroutineScope(Dispatchers.IO)
        val mainScope = CoroutineScope(Dispatchers.Main)
        val weatherAPIClient = NetworkingClient()
        savedCities.executeAsList().forEach { city ->
            asyncScope.launch {
                val weatherResponse = weatherAPIClient.getWeatherDataForCity(city.serviceUrl)
                mainScope.launch {
                    cityListInternal.add(
                        SavedCityItem(
                            cityID = city.cityID,
                            cityName = city.cityName.split(",")[0],
                            stateName = city.stateName,
                            countryName = city.countryName,
                            serviceUrl = city.serviceUrl,
                            weatherResponse = weatherResponse
                        )
                    )
                }
            }
        }
    }

}