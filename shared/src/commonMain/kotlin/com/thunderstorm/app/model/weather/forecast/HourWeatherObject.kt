package com.thunderstorm.app.model.weather.forecast

import com.thunderstorm.app.model.weather.special.ConditionWeatherObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HourWeatherObject(
    @SerialName("time_epoch")
    var timeEpoch: Long,
    @SerialName("time")
    var localTime: String,
    @SerialName("temp_c")
    var temperatureCelsius: Double,
    @SerialName("temp_f")
    var temperatureFahrenheit: Double,
    @SerialName("is_day")
    var isDay: Int,
    @SerialName("condition")
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
    @SerialName("windchill_c")
    var windChillCelsius: Double,
    @SerialName("windchill_f")
    var windChillFahrenheit: Double,
    @SerialName("heatindex_c")
    var heatIndexCelsius: Double,
    @SerialName("heatindex_f")
    var heatIndexFahrenheit: Double,
    @SerialName("dewpoint_c")
    var dewPointCelsius: Double,
    @SerialName("dewpoint_f")
    var dewPointFahrenheit: Double,
    @SerialName("will_it_rain")
    var willItRain: Int,
    @SerialName("chance_of_rain")
    var chanceOfRain: Long,
    @SerialName("will_it_snow")
    var willItSnow: Int,
    @SerialName("chance_of_snow")
    var chanceOfSnow: Long,
    @SerialName("vis_km")
    var visibilityKm: Double,
    @SerialName("vis_miles")
    var visibilityMi: Double,
    @SerialName("gust_mph")
    var windGustMph: Double,
    @SerialName("gust_kph")
    var windGustKph: Double,
    var uv: Double
)
