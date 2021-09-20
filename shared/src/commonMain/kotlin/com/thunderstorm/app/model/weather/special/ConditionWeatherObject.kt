package com.thunderstorm.app.model.weather.special

import kotlinx.serialization.Serializable

@Serializable
data class ConditionWeatherObject(
    var text: String,
    var icon: String,
    var code: Long
)