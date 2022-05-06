package com.thunderstorm.app.android.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import com.thunderstorm.app.model.SearchCityResult
import com.thunderstorm.app.networking.NetworkingClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SetupViewModel : ViewModel() {

    val searchFieldValue = mutableStateOf(TextFieldValue(""))
    val selectedCity = mutableStateOf<SearchCityResult?>(null)
    val cityAutocompleteItems = mutableStateOf(mutableListOf<SearchCityResult>())
    val allowNavigateNext = mutableStateOf(false)

    private val asyncScope = CoroutineScope(Dispatchers.IO + Job())

    fun fetchCitiesForSearch() {
        asyncScope.launch {
            NetworkingClient().getMatchingCitiesForSearch(
                query = searchFieldValue.value.text,
                onResultAvailable = { result ->
                    cityAutocompleteItems.value.clear()
                    result.forEach { searchCityResult ->
                        cityAutocompleteItems.value.add(searchCityResult)
                    }
                }
            )
        }
    }

}