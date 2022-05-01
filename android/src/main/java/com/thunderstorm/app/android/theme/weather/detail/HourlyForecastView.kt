package com.thunderstorm.app.android.theme.weather.detail

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.utils.getIconForNameAndCode
import com.thunderstorm.app.android.viewmodel.WeatherViewModel
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.model.weather.forecast.HourWeatherObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun HourlyForecastView(
    viewModel: WeatherViewModel,
    context: Activity
) {
    val weatherData = viewModel.forecastWeatherData.value
    val dataStore = DataStore(context)
    fun getWeatherData(day: Int): List<HourWeatherObject>?
        = weatherData?.forecast?.forecastDay?.get(day)?.hourDetails
    Column {
        Box {
            val scrollState = rememberScrollState()
            Row(
                content = {
                    getWeatherData(0)?.forEachIndexed { _, item ->
                        ProcessHourListItem(
                            item = item,
                            context = context,
                            dataStore = dataStore
                        )
                    }
                    NextDayWeatherCard(
                        localTime = getWeatherData(1)?.get(0)?.localTime
                    )
                    getWeatherData(1)?.forEachIndexed { _, item ->
                        HourlyListItem(
                            weatherData = item,
                            context = context,
                            dataStore = dataStore
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 35.dp, end = 10.dp)
                    .horizontalScroll(scrollState)
                    .placeholder(
                        visible = !viewModel.showWeatherData.value,
                        shape = RoundedCornerShape(10.dp),
                        highlight = PlaceholderHighlight.shimmer()
                    )
            )
            Box(
                modifier = Modifier
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color.Transparent,
                                MaterialTheme.colors.background
                            ),
                            startX = 0.0F,
                            endX = 90.0F
                        )
                    )
                    .padding(end = 10.dp)
                    .width(40.dp)
                    .height(170.dp)
                    .align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
fun NextDayWeatherCard(
    localTime: String?
) {
    Column(
        modifier = Modifier
            .padding(end = 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                colorResource(id = R.color.interface_gray_alt).copy(
                    0.5F
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val localTimeData = localTime?.split("-")
        Text(
            text = """${localTimeData?.get(1)} / ${
                localTimeData?.get(2)?.split(" ")?.get(0)
            }""",
            style = MaterialTheme.typography.body2,
            fontSize = 15.sp,
            modifier = Modifier.padding(
                top = 10.dp,
                start = 12.dp,
                end = 10.dp
            )
        )
        Icon(
            painter = painterResource(id = R.drawable.ic_double_arrow_next),
            contentDescription = stringResource(id = R.string.double_arrow_next_content_desc),
            modifier = Modifier
                .padding(top = 30.dp, bottom = 33.dp)
                .size(34.dp)
        )
    }
}

@Composable
private fun ProcessHourListItem(
    item: HourWeatherObject,
    context: Context,
    dataStore: DataStore
) {
    @Composable
    fun hourListItem() {
        HourlyListItem(
            weatherData = item,
            context = context,
            dataStore = dataStore
        )
    }
    val usTimeFormat = SimpleDateFormat("h a", Locale.getDefault())
    val militaryTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val targetTimeHour = usTimeFormat.format(militaryTimeFormat.parse(item.localTime.split(" ")[1])!!)
    val currentTimeHour = usTimeFormat.format(Date())
    val targetAMPM = targetTimeHour.split(" ")[1]
    val currentAMPM = currentTimeHour.split(" ")[1]
    if (
        targetAMPM == currentAMPM &&
        currentTimeHour.split(" ")[0].toInt() <= targetTimeHour.split(" ")[0].toInt()
    ) {
        if ((currentAMPM == "AM" && targetTimeHour != "12 $currentAMPM") ||
            (targetTimeHour != "12 $currentAMPM")
        ) {
            hourListItem()
        }
    }
    if (currentAMPM == "AM" && targetAMPM == "PM") {
        hourListItem()
    }
}

@Composable
fun HourlyListItem(
    weatherData: HourWeatherObject,
    context: Context,
    dataStore: DataStore
) {
    val weatherIcon = context.getIconForNameAndCode(
        weatherData.isDay,
        weatherData.condition.code
    )
    val usTimeFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    val militaryTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val parsedTime = usTimeFormat.format(
        militaryTimeFormat.parse(weatherData.localTime.split(" ")[1])!!
    )
    Column(
        modifier = Modifier
            .padding(end = 15.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(colorResource(id = R.color.interface_gray).copy(0.5F)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = """${parsedTime.split(":")[0]} ${parsedTime.split(" ")[1]}""",
            style = MaterialTheme.typography.body2,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 10.dp)
        )
        Image(
            painter = painterResource(id = weatherIcon),
            contentDescription = null,
            modifier = Modifier
                .padding(top = 15.dp, end = 12.dp, start = 12.dp)
                .size(40.dp)
        )
        Text(
            text = String.format(
                stringResource(id = R.string.weather_temperature_template),
                if (dataStore.getInteger("PREF_TEMP_UNITS") == 0) {
                    weatherData.temperatureCelsius.roundToInt()
                } else {
                    weatherData.temperatureFahrenheit.roundToInt()
                }
            ),
            style = MaterialTheme.typography.body2,
            fontSize = 15.sp,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        )
    }
}