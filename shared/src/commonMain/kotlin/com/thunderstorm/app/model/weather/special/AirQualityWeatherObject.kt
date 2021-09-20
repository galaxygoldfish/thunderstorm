package com.thunderstorm.app.model.weather.special

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirQualityWeatherObject(
    var co: Double,
    var no2: Double,
    var o3: Double,
    var so2: Double,
    var pm2_5: Double,
    var pm10: Double,
    @SerialName("us-epa-index")
    var usEpaIndex: Long,
    @SerialName("gb-defra-index")
    var gbDefraIndex: Long
)