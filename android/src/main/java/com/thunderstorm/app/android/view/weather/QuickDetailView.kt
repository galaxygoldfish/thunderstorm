package com.thunderstorm.app.android.view.weather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thunderstorm.app.android.R
import com.thunderstorm.app.model.weather.WeatherDataResult
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun QuickDetailView(
    weatherData: WeatherDataResult
) {
    AstronomyDetailCard(
        weatherData = weatherData
    )
    Column(
        modifier = Modifier.padding(top = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            QuickDetailCard(
                mainText = """${weatherData.current.humidity}%""",
                subtitle = stringResource(id = R.string.weather_humidity_detail_text),
                position = true
            )
            QuickDetailCard(
                mainText = """${weatherData.current.uv.roundToInt()}""",
                subtitle = stringResource(id = R.string.weather_uv_detail_text),
                position = false
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            val airQualityLevel = weatherData.current.airQuality.usEpaIndex.toInt()
            QuickDetailCard(
                mainText = stringArrayResource(id = R.array.air_quality_units_epa)[airQualityLevel - 1],
                subtitle = stringResource(id = R.string.weather_air_quality_detail_text),
                position = true,
                airQualityText = true
            )
            QuickDetailCard(
                mainText = """${weatherData.current.visibilityMi.roundToInt()} mi""",
                subtitle = stringResource(id = R.string.weather_uv_detail_text),
                position = false
            )
        }
    }
}

@Composable
fun AstronomyDetailCard(
    weatherData: WeatherDataResult
) {
    val initialAstroTime = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val targetAstroTime = SimpleDateFormat("h:mm a", Locale.getDefault())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                colorResource(id = R.color.interface_gray).copy(0.5F)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_sun_icon),
                contentDescription = stringResource(id = R.string.sun_icon_content_desc),
                modifier = Modifier.padding(
                    start = 20.dp,
                    top = 20.dp,
                    bottom = 20.dp
                )
            )
            Text(
                text = targetAstroTime.format(
                    initialAstroTime.parse(weatherData.forecast.forecastDay[0].astronomy.sunrise)!!
                ),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
        Column(
            modifier = Modifier
                .size(width = 1.dp, height = 40.dp)
                .background(
                    MaterialTheme.colors.onBackground.copy(0.3F)
                ),
            content = {}
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = targetAstroTime.format(
                    initialAstroTime.parse(weatherData.forecast.forecastDay[0].astronomy.sunset)!!
                ),
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(end = 20.dp)
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_moon_cloudy_icon),
                contentDescription = stringResource(id = R.string.moon_icon_content_desc),
                modifier = Modifier.padding(
                    end = 20.dp,
                    top = 20.dp,
                    bottom = 20.dp
                )
            )
        }
    }
}

@Composable
fun QuickDetailCard(
    mainText: String,
    subtitle: String,
    position: Boolean,
    airQualityText: Boolean? = false
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