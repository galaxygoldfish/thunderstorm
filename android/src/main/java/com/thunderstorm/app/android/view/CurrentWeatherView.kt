package com.thunderstorm.app.android.view

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.viewmodel.WeatherViewModel
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.database.datastore.SharedContext
import kotlin.math.roundToInt

@Composable
fun CurrentWeatherView(viewModel: WeatherViewModel, context: Activity) {
    val weatherData = viewModel.forecastWeatherData
    val dataStore = DataStore(context.applicationContext as SharedContext)
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp)
    ) {
        Text(
            text = String.format(
                stringResource(id = R.string.weather_temperature_template),
                if (dataStore.getBoolean("USE_IMPERIAL_UNITS")) {
                    weatherData?.current?.tempFarenheit?.roundToInt()
                } else {
                    weatherData?.current?.tempCelsius?.roundToInt()
                }
            ),
            style = MaterialTheme.typography.h1,
            modifier = Modifier
                .padding(start = 20.dp)
                .align(Alignment.CenterVertically)
                .placeholder(
                    visible = !viewModel.showWeatherData,
                    shape = RoundedCornerShape(10.dp),
                    highlight = PlaceholderHighlight.shimmer()
                )
        )
        Image(
            painter = painterResource(id = viewModel.currentIconResource),
            contentDescription = null,
            modifier = Modifier
                .padding(end = 20.dp)
                .size(120.dp)
                .align(Alignment.CenterVertically)
                .placeholder(
                    visible = !viewModel.showWeatherData,
                    shape = RoundedCornerShape(10.dp),
                    highlight = PlaceholderHighlight.shimmer()
                )
        )
    }
    Text(
        text = weatherData?.current?.condition?.text ?: "\t\t",
        modifier = Modifier
            .padding(start = 22.dp)
            .placeholder(
                visible = !viewModel.showWeatherData,
                shape = RoundedCornerShape(10.dp),
                highlight = PlaceholderHighlight.shimmer()
            ),
        style = MaterialTheme.typography.subtitle1
    )
    Text(
        text = String.format(
            format = stringResource(id = R.string.weather_feels_like_template),
            if (dataStore.getBoolean("USE_IMPERIAL_UNITS")) {
                weatherData?.current?.feelsLikeFarenheit?.roundToInt() ?: "\t\t\t"
            } else {
                weatherData?.current?.feelsLikeCelsius?.roundToInt() ?: "\t\t\t"
            }
        ),
        style = MaterialTheme.typography.body1,
        fontSize = 15.sp,
        color = LocalContentColor.current.copy(0.6F),
        modifier = Modifier
            .padding(start = 22.dp)
            .placeholder(
                visible = !viewModel.showWeatherData,
                shape = RoundedCornerShape(10.dp),
                highlight = PlaceholderHighlight.shimmer()
            )
    )
}