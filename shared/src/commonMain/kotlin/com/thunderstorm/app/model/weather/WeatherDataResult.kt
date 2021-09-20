package com.thunderstorm.app.model.weather

import com.thunderstorm.app.model.weather.current.CurrentWeatherObject
import com.thunderstorm.app.model.weather.forecast.ForecastWeatherObject
import com.thunderstorm.app.model.weather.special.AlertWeatherObject
import com.thunderstorm.app.model.weather.special.AlertWeatherResult
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDataResult(
    var current: CurrentWeatherObject,
    var forecast: ForecastWeatherObject,
    var alerts: AlertWeatherResult
)
