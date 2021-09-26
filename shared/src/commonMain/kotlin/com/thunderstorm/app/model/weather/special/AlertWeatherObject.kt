package com.thunderstorm.app.model.weather.special

import kotlinx.serialization.Serializable

@Serializable
data class AlertWeatherResult(
    var alert: List<AlertWeatherObject?>?
)

@Serializable
data class AlertWeatherObject(
    var headline: String,
    var msgtype: String,
    var severity: String,
    var urgency: String,
    var areas: String,
    var category: String,
    var certainty: String,
    var event: String,
    var note: String,
    var effective: String,
    var expires: String,
    var desc: String,
    var instruction: String
)
