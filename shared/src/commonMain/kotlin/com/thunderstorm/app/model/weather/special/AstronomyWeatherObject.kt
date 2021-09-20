package com.thunderstorm.app.model.weather.special

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AstronomyWeatherObject(
    var sunrise: String,
    var sunset: String,
    var moonrise: String,
    var moonset: String,
    @SerialName("moon_phase")
    var moonPhase: String,
    @SerialName("moon_illumination")
    var moonIllumination: String
)
