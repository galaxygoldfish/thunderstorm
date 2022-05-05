package com.thunderstorm.app.model.weather.special

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AirQualityWeatherObject(
    var co: Double? = null,
    var no2: Double? = null,
    var o3: Double? = null,
    var so2: Double? = null,
    var pm2_5: Double? = null,
    var pm10: Double? = null,
    @SerialName("us-epa-index")
    var usEpaIndex: Long = 0,
    @SerialName("gb-defra-index")
    var gbDefraIndex: Long = 0
)