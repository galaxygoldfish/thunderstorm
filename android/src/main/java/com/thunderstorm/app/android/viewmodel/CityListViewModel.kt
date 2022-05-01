package com.thunderstorm.app.android.viewmodel

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.thunderstorm.app.ThunderstormDatabase
import com.thunderstorm.app.database.DatabaseDriver
import com.thunderstorm.app.model.SavedCityItem
import com.thunderstorm.app.model.weather.WeatherDataResult
import com.thunderstorm.app.networking.NetworkingClient
import kotlinx.coroutines.*

class CityListViewModel : ViewModel() {

    var savedCityList = mutableStateListOf<SavedCityItem>()

    fun loadSavedCities(context: Context) {
        val database = ThunderstormDatabase(DatabaseDriver(context).createDriver())
        val savedCities = database.cityStoreQueries.getAllCities()
        savedCityList.clear()
        savedCities.executeAsList().forEach { city ->
            savedCityList.add(
                SavedCityItem(
                    cityID = city.cityID,
                    cityName = city.cityName.split(",")[0],
                    stateName = city.stateName,
                    countryName = city.countryName,
                    serviceUrl = city.serviceUrl
                )
            )
        }
    }

    suspend fun fetchDataForCard(cityName: String) : WeatherDataResult {
        val weatherAPIClient = NetworkingClient()
        return weatherAPIClient.getWeatherDataForCity(cityName)
    }

}