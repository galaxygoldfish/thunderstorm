package com.thunderstorm.app.model.weather.forecast

import com.thunderstorm.app.model.weather.special.AstronomyWeatherObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForecastDayWeatherObject(
    var date: String,
    @SerialName("date_epoch")
    var timeEpoch: Long,
    @SerialName("day")
    var dayDetails: DailyWeatherObject,
    @SerialName("astro")
    var astronomy: AstronomyWeatherObject,
    @SerialName("hour")
    var hourDetails: List<HourWeatherObject>
)