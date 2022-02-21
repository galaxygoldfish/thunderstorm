package com.thunderstorm.app.wear.viewmodel

import android.widget.SearchView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.thunderstorm.app.ThunderstormDatabase
import com.thunderstorm.app.database.DatabaseDriver
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.model.SearchCityResult
import com.thunderstorm.app.networking.NetworkingClient
import com.thunderstorm.app.wear.NavigationDestination
import com.thunderstorm.app.wear.ThunderstormBaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCityViewModel : ViewModel() {

    var citySearchTextValue by mutableStateOf("")
    var cityResultList = mutableStateListOf<SearchCityResult>()
    var isLoadingCityResult by mutableStateOf(true)
    var selectedCity by mutableStateOf<SearchCityResult?>(null)

    fun reloadSearch() {
        CoroutineScope(Dispatchers.Default).launch {
            isLoadingCityResult = true
            NetworkingClient().getMatchingCitiesForSearch(
                query = citySearchTextValue,
                onResultAvailable = { result ->
                    cityResultList.clear()
                    result.forEach { searchCityResult ->
                        cityResultList.add(searchCityResult)
                    }
                    isLoadingCityResult = false
                }
            )
        }
    }

    fun navigateToWeather(navController: NavController) {
        val dataStore = DataStore(navController.context as ThunderstormBaseActivity)
        val cityDatabase = ThunderstormDatabase(DatabaseDriver(navController.context).createDriver())
        selectedCity?.apply {
            cityDatabase.cityStoreQueries.insertNewCity(name, region, country, url)
            dataStore.putBoolean("INDICATION_ONBOARDING_DONE", true)
            navController.navigate(NavigationDestination.WeatherView)
        }
    }

}