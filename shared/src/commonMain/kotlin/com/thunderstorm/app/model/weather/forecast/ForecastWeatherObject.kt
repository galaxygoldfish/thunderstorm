package com.thunderstorm.app.model.weather.forecast

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastWeatherObject(
    @SerialName("forecastday")
    var forecastDay: List<ForecastDayWeatherObject>
)