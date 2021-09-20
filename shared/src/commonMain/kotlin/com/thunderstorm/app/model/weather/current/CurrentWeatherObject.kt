package com.thunderstorm.app.model.weather.current

import com.thunderstorm.app.model.weather.special.AirQualityWeatherObject
import com.thunderstorm.app.model.weather.special.ConditionWeatherObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CurrentWeatherObject(
    @SerialName("last_updated_epoch")
    var lastUpdatedEpoch: Long,
    @SerialName("last_updated")
    var lastUpdated: String,
    @SerialName("temp_c")
    var tempCelsius: Double,
    @SerialName("temp_f")
    var tempFarenheit: Double,
    @SerialName("is_day")
    var isDay: Int,
    var condition: ConditionWeatherObject,
    @SerialName("wind_mph")
    var windMph: Double,
    @SerialName("wind_kph")
    var windKph: Double,
    @SerialName("wind_dir")
    var windDir: String,
    @SerialName("pressure_mb")
    var pressureMb: Double,
    @SerialName("pressure_in")
    var pressureIn: Double,
    @SerialName("precip_mm")
    var precipMm: Double,
    @SerialName("precip_in")
    var precipIn: Double,
    var humidity: Long,
    var cloud: Long,
    @SerialName("feelslike_c")
    var feelsLikeCelsius: Double,
    @SerialName("feelslike_f")
    var feelsLikeFarenheit: Double,
    @SerialName("vis_km")
    var visibilityKm: Double,
    @SerialName("vis_miles")
    var visibilityMi: Double,
    var uv: Double,
    @SerialName("air_quality")
    var airQuality: AirQualityWeatherObject
)
