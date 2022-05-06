package com.thunderstorm.app.android.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.thunderstorm.app.ThunderstormDatabase
import com.thunderstorm.app.database.DatabaseDriver
import com.thunderstorm.app.model.SearchCityResult
import com.thunderstorm.app.networking.NetworkingClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CityAddViewModel : ViewModel() {

    var currentSearchQuery by mutableStateOf(TextFieldValue())
    var citySearchResult = mutableStateListOf<SearchCityResult>()
    var showDoneDialog by mutableStateOf(false)
    var currentSelectedCity by mutableStateOf<SearchCityResult?>(null)
    var saveCityInProgress by mutableStateOf(false)

    fun searchCities() {
        CoroutineScope(Dispatchers.Default).launch {
            NetworkingClient().getMatchingCitiesForSearch(
                query = currentSearchQuery.text,
                onResultAvailable = { result ->
                    citySearchResult.clear()
                    result.forEach { searchCityResult ->
                        citySearchResult.add(searchCityResult)
                    }
                }
            )
        }
    }

    fun saveCity(city: SearchCityResult, context: Context) {
        saveCityInProgress = true
        CoroutineScope(Dispatchers.IO).launch {
            city.apply {
                val cityDatabase = ThunderstormDatabase(DatabaseDriver(context).createDriver())
                cityDatabase.cityStoreQueries.insertNewCity(name, region, country, url!!)
            }
        }
        showDoneDialog = false
        saveCityInProgress = false
        citySearchResult.clear()
        currentSearchQuery = TextFieldValue()
        currentSelectedCity = null
    }

}