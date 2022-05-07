package com.thunderstorm.app.android.view.weather.detail

import android.app.Activity
import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.fade
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.theme.TexGyreHeros
import com.thunderstorm.app.android.utils.getIconForNameAndCode
import com.thunderstorm.app.android.viewmodel.WeatherViewModel
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.database.datastore.SharedContext
import com.thunderstorm.app.model.weather.forecast.ForecastDayWeatherObject
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun DailyForecastView(viewModel: WeatherViewModel, context: Activity) {
    val weatherData = viewModel.forecastWeatherData
    val dataStore = DataStore(context.applicationContext as SharedContext)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .placeholder(
                visible = !viewModel.showWeatherData,
                shape = RoundedCornerShape(10.dp),
                highlight = PlaceholderHighlight.shimmer()
            )
    ) {
        weatherData?.forecast?.forecastDay?.forEach { item ->
            DailyListItem(
                weatherData = item,
                isDay = weatherData.current.isDay,
                context = context,
                dataStore = dataStore
            )
        }
    }
}

@Composable
fun DailyListItem(
    weatherData: ForecastDayWeatherObject,
    isDay: Int,
    context: Context,
    dataStore: DataStore
) {
    val parsedDate = weatherData.hourDetails[0].localTime.let { details ->
        val dayOfMonth = details.substring(8, 10)
        val parsedDay = dayOfMonth.toInt() - 2
        details.replace(dayOfMonth, parsedDay.toString())
    }

    val weekdayFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    val weatherServiceFormat = SimpleDateFormat("yyyy-mm-dd hh:mm", Locale.getDefault())
    val weekdayParsed = weekdayFormat.format(weatherServiceFormat.parse(parsedDate)!!)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp, start = 20.dp, end = 20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(colorResource(id = R.color.interface_gray).copy(0.5F))
            .border(
                border = BorderStroke(3.dp, colorResource(id = R.color.interface_gray)),
                shape = RoundedCornerShape(10.dp)
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(
                    id = context.getIconForNameAndCode(
                        isDay,
                        weatherData.dayDetails.condition.code
                    )
                ),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, bottom = 15.dp)
                    .size(30.dp)
            )
            Text(
                text = weekdayParsed,
                modifier = Modifier.padding(start = 15.dp)
            )
        }
        Row(
            modifier = Modifier.padding(end = 20.dp)
        ) {
            weatherData.dayDetails.let { data ->
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = TexGyreHeros
                            )
                        ) {
                            append(
                                """${
                                    if (dataStore.getBoolean("USE_IMPERIAL_UNITS")) {
                                        data.highTempFahrenheit.roundToInt()
                                    } else {
                                        data.highTempCelsius.roundToInt()
                                    }
                                }° """
                            )
                        }
                        withStyle(
                            SpanStyle(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = TexGyreHeros,
                                color = LocalContentColor.current.copy(0.7F)
                            )
                        ) {
                            append("""${
                                if (dataStore.getBoolean("USE_IMPERIAL_UNITS")) {
                                    data.lowTempFahrenheit.roundToInt()
                                } else {
                                    data.lowTempCelsius.roundToInt()
                                }
                            }°""")
                        }
                    }
                )
            }
        }
    }
}