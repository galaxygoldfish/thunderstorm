package com.thunderstorm.app.android.view

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.utils.getIconForNameAndCode
import com.thunderstorm.app.android.viewmodel.WeatherViewModel
import com.thunderstorm.app.model.weather.forecast.HourWeatherObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.roundToInt
import kotlinx.coroutines.launch

@Composable
fun WeatherView(
    viewModel: WeatherViewModel,
    navController: NavController
) {
    viewModel.loadDefaultCity(navController.context)
    val scaffoldState = rememberScaffoldState()
    val coroutineScope =  rememberCoroutineScope()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = viewModel.currentCityName.value.toString(),
                modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                style = MaterialTheme.typography.h3
            )
            Text(
                text = viewModel.currentRegionName.value.toString(),
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 1.dp, start = 20.dp),
                fontSize = 14.sp
            )
            if (viewModel.forecastWeatherData.value != null) {
                val currentWeatherData = viewModel.forecastWeatherData.value!!.current
                val hourlyWeatherData = viewModel.forecastWeatherData.value!!.forecast
                Column {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 40.dp)
                    ) {
                        Text(
                            text = currentWeatherData.tempFarenheit.toInt().toString() + "°",
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .align(Alignment.CenterVertically)
                        )
                        Image(
                            painter = painterResource(id = viewModel.currentIconResource.value),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(end = 20.dp)
                                .size(120.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                    Text(
                        text = currentWeatherData.condition.text,
                        modifier = Modifier.padding(start = 22.dp),
                        style = MaterialTheme.typography.subtitle1
                    )
                    Text(
                        text = String.format(
                            format = stringResource(id = R.string.weather_feels_like_template),
                            currentWeatherData.feelsLikeFarenheit.roundToInt().toString()
                        ),
                        style = MaterialTheme.typography.body1,
                        fontSize = 15.sp,
                        color = LocalContentColor.current.copy(0.6F),
                        modifier = Modifier.padding(start = 22.dp)
                    )
                    Box {
                        val scrollState = rememberScrollState()
                        Row(
                            content = {
                                hourlyWeatherData.forecastDay[0].hourDetails.forEachIndexed { index, item ->
                                    HourlyListItem(
                                        weatherData = item,
                                        context = navController.context
                                    )
                                }
                            },
                            modifier = Modifier
                                .padding(start = 20.dp, top = 35.dp, end = 10.dp)
                                .horizontalScroll(scrollState)
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
                                .fillMaxHeight()
                                .align(Alignment.CenterEnd)
                        )
                    }
                }
            } else {
                LottieAnimation(
                    rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_anim)).value,
                    modifier = Modifier
                        .fillMaxHeight()
                        .size(100.dp)
                        .align(Alignment.CenterHorizontally)
                        .size(100.dp)
                )
            }
        }
    }
}

@Composable
fun HourlyListItem(
    weatherData: HourWeatherObject,
    context: Context
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
            text = weatherData.temperatureFahrenheit.roundToInt().toString() + "°",
            style = MaterialTheme.typography.body2,
            fontSize = 15.sp,
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
        )
    }
}