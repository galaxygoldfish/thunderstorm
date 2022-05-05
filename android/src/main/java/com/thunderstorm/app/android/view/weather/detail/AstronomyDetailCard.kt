package com.thunderstorm.app.android.view.weather.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.viewmodel.WeatherViewModel
import com.thunderstorm.app.model.weather.WeatherDataResult
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun AstronomyDetailCard(
    viewModel: WeatherViewModel
) {
    val weatherData = viewModel.forecastWeatherData
    val initialAstroTime = SimpleDateFormat("hh:mm a", Locale.getDefault())
    val targetAstroTime = SimpleDateFormat("h:mm a", Locale.getDefault())
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                colorResource(id = R.color.interface_gray).copy(0.5F)
            )
            .placeholder(
                visible = !viewModel.showWeatherData,
                shape = RoundedCornerShape(10.dp),
                highlight = PlaceholderHighlight.shimmer()
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
                    initialAstroTime.parse(weatherData?.forecast?.forecastDay?.get(0)?.astronomy?.sunrise ?: "12:00 AM")
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
                    initialAstroTime.parse(weatherData?.forecast?.forecastDay?.get(0)?.astronomy?.sunset ?: "12:00 AM")
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