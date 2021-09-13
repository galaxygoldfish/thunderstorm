package com.thunderstorm.app.android.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WeatherViewModel : ViewModel() {

    val currentCityName: MutableLiveData<String> = MutableLiveData()

}