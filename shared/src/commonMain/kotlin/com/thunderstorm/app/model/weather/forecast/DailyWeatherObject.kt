package com.thunderstorm.app.model.weather.forecast

import com.thunderstorm.app.model.weather.special.ConditionWeatherObject
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyWeatherObject(
    @SerialName("maxtemp_c")
    var highTempCelsius: Double,
    @SerialName("maxtemp_f")
    var highTempFahrenheit: Double,
    @SerialName("mintemp_c")
    var lowTempCelsius: Double,
    @SerialName("mintemp_f")
    var lowTempFahrenheit: Double,
    @SerialName("avgtemp_c")
    var averageTempCelsius: Double,
    @SerialName("avgtemp_f")
    var averageTempFahrenheit: Double,
    @SerialName("maxwind_mph")
    var maxWindMph: Double,
    @SerialName("maxwind_kph")
    var maxWindKph: Double,
    @SerialName("totalprecip_mm")
    var totalPrecipitationMm: Double,
    @SerialName("totalprecip_in")
    var totalPrecipitationIn: Double,
    @SerialName("avghumidity")
    var averageHumidity: Double,
    @SerialName("daily_will_it_rain")
    var willItRain: Int,
    @SerialName("daily_chance_of_rain")
    var chanceOfRain: Long,
    @SerialName("daily_will_it_snow")
    var willItSnow: Int,
    @SerialName("daily_chance_of_snow")
    var chanceOfSnow: Long,
    @SerialName("condition")
    var condition: ConditionWeatherObject,
    @SerialName("uv")
    var uvIndex: Double
)
