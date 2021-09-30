package com.thunderstorm.app.networking

import com.thunderstorm.app.Keystore
import com.thunderstorm.app.model.SearchCityResult
import com.thunderstorm.app.model.weather.WeatherDataResult
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.*
import kotlinx.serialization.json.Json

class NetworkingClient {

    private val httpClient = HttpClient {
        install(JsonFeature) {
            val json = Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    fun getMatchingCitiesForSearch(
        query: String?,
        onResultAvailable: (List<SearchCityResult>) -> Unit
    ) {
        if (query != null) {
            CoroutineScope(Dispatchers.Default).launch {
                val result = httpClient.get<List<SearchCityResult>>("""${WeatherAPIEndpoints.BASE_URL}${WeatherAPIEndpoints.EXTENSION_SEARCH}""") {
                    parameter("q", query)
                    parameter("key", Keystore.WeatherAPIKey)
                }
                CoroutineScope(Dispatchers.Main).launch {
                    onResultAvailable.invoke(result)
                }
            }
        }
    }

    suspend fun getWeatherDataForCity(query: String) : WeatherDataResult {
        return httpClient.get("""${WeatherAPIEndpoints.BASE_URL}${WeatherAPIEndpoints.EXTENSION_FORECAST}""") {
            parameter("q", query)
            parameter("key", Keystore.WeatherAPIKey)
            parameter("aqi", "yes")
            parameter("days", 4)
            parameter("alerts", "yes")
        }
    }

}
