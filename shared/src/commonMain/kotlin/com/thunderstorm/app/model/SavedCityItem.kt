package com.thunderstorm.app.model

import com.thunderstorm.app.model.weather.WeatherDataResult

data class SavedCityItem(
    var cityID: Long,
    var cityName: String,
    var stateName: String,
    var countryName: String,
    var serviceUrl: String
)
