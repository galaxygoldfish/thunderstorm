package com.thunderstorm.app.android.viewmodel

import android.content.Context
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

    val cityList: MutableState<MutableList<SavedCityItem>> = mutableStateOf(mutableListOf())

    fun loadSavedCities(context: Context) {
        val database = ThunderstormDatabase(DatabaseDriver(context).createDriver())
        val savedCities = database.cityStoreQueries.getAllCities().executeAsList()
        val asyncScope = CoroutineScope(Dispatchers.IO)
        val mainScope = CoroutineScope(Dispatchers.Main)
        val weatherAPIClient = NetworkingClient()
        val cityListTemp = cityList.value
        savedCities.forEach { city ->
            asyncScope.launch {
                val weatherResponse = weatherAPIClient.getWeatherDataForCity(city.serviceUrl)
                mainScope.launch {
                    cityListTemp.add(
                        SavedCityItem(
                            cityID = city.cityID,
                            cityName = city.cityName.split(",")[0],
                            stateName = city.stateName,
                            countryName = city.countryName,
                            serviceUrl = city.serviceUrl,
                            weatherResponse = weatherResponse
                        )
                    )
                    cityList.value = cityListTemp
                }
            }
        }
    }

}