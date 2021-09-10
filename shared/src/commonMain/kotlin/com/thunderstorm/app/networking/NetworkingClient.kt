package com.thunderstorm.app.networking

import com.thunderstorm.app.Keystore
import com.thunderstorm.app.model.SearchCityResult
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import kotlinx.serialization.json.Json

class NetworkingClient {

    private val httpClient = HttpClient {
        install(JsonFeature) {
            val json = Json { ignoreUnknownKeys = true }
            serializer = KotlinxSerializer(json)
        }
    }

    suspend fun getMatchingCitiesForSearch(query: String?) : List<SearchCityResult> {
        return if (query != null) {
            httpClient.get("${WeatherAPIEndpoints.BASE_URL}${WeatherAPIEndpoints.EXTENSION_SEARCH}?q=$query&key=${Keystore.WeatherAPIKey}")
        } else {
            listOf()
        }
    }

}
