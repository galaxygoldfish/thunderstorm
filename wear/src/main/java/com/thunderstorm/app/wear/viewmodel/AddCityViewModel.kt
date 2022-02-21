package com.thunderstorm.app.wear.viewmodel

import android.widget.SearchView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.thunderstorm.app.model.SearchCityResult
import com.thunderstorm.app.networking.NetworkingClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddCityViewModel : ViewModel() {

    var citySearchTextValue by mutableStateOf("")
    var cityResultList = mutableStateListOf<SearchCityResult>()

    fun reloadSearch() {
        CoroutineScope(Dispatchers.Default).launch {
            NetworkingClient().getMatchingCitiesForSearch(
                query = citySearchTextValue,
                onResultAvailable = { result ->
                    cityResultList.clear()
                    result.forEach { searchCityResult ->
                        cityResultList.add(searchCityResult)
                    }
                }
            )
        }
    }

}