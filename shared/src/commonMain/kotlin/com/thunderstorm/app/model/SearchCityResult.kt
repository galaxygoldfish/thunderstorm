package com.thunderstorm.app.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchCityResult(
    val name: String,
    val region: String,
    val country: String,
    val lat: Double,
    val lon: Double,
    val url: String? = null
)