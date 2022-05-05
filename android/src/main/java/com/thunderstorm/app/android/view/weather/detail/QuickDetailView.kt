package com.thunderstorm.app.android.view.weather

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.viewmodel.WeatherViewModel
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.database.datastore.DataStoreName
import kotlin.math.roundToInt

@Composable
fun QuickDetailView(viewModel: WeatherViewModel, context: Activity) {
    val weatherData = viewModel.forecastWeatherData
    val dataStore = DataStore(context)
    Column(
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            QuickDetailCard(
                mainText = """${weatherData?.current?.humidity}%""",
                subtitle = stringResource(id = R.string.weather_humidity_detail_text),
                position = true,
                viewModel = viewModel
            )
            QuickDetailCard(
                mainText = """${weatherData?.current?.uv?.roundToInt()}""",
                subtitle = stringResource(id = R.string.weather_uv_detail_text),
                position = false,
                viewModel = viewModel
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            val airQualityLevel = weatherData?.current?.airQuality?.usEpaIndex?.toInt()
            QuickDetailCard(
                mainText = stringArrayResource(id = R.array.air_quality_units_epa)[airQualityLevel?.minus(1) ?: 0],
                subtitle = stringResource(id = R.string.weather_air_quality_detail_text),
                position = true,
                airQualityText = true,
                viewModel = viewModel
            )
            QuickDetailCard(
                mainText = """${
                    if (dataStore.getInteger("PREF_SPEED_UNITS") == 0) {
                        weatherData?.current?.visibilityMi?.roundToInt()
                    } else {
                        weatherData?.current?.visibilityKm?.roundToInt()
                    }
                } mi""",
                subtitle = stringResource(id = R.string.weather_visibility_detail_text),
                position = false,
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun QuickDetailCard(
    mainText: String,
    subtitle: String,
    position: Boolean,
    airQualityText: Boolean? = false,
    viewModel: WeatherViewModel
) {
    val startPadding = if (position) 20.dp else 5.dp
    val endPadding = if (position) 5.dp else 20.dp
    Column(
        modifier = Modifier
            .fillMaxWidth(if (position) 0.5F else 1.0F)
            .padding(top = 10.dp, end = endPadding, start = startPadding)
            .clip(RoundedCornerShape(10.dp))
            .background(
                colorResource(id = R.color.interface_gray).copy(0.5F)
            )
            .placeholder(
                visible = !viewModel.showWeatherData,
                shape = RoundedCornerShape(10.dp),
                highlight = PlaceholderHighlight.shimmer()
            )
            .border(
                border = BorderStroke(3.dp, colorResource(id = R.color.interface_gray)),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Text(
            text = mainText,
            style = MaterialTheme.typography.h4,
            fontSize = if (airQualityText!!) 27.sp else 33.sp,
            modifier = Modifier.padding(start = 15.dp, top = if (airQualityText) 10.dp else 5.dp)
        )
        Text(
            text = subtitle,
            modifier = Modifier.padding(start = 15.dp, bottom = 15.dp, top = if (airQualityText) 3.dp else 0.dp)
        )
    }
}