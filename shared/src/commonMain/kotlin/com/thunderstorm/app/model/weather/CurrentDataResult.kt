package com.thunderstorm.app.model.weather

import kotlinx.serialization.Serializable

@Serializable
data class CurrentDataResult(
    var current: CurrentWeatherObject
)
