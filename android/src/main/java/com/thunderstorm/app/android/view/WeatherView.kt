package com.thunderstorm.app.android.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.google.accompanist.pager.ExperimentalPagerApi
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.presentation.ThunderstormBaseActivity
import com.thunderstorm.app.android.view.weather.CurrentWeatherView
import com.thunderstorm.app.android.view.weather.DailyForecastView
import com.thunderstorm.app.android.view.weather.HourlyForecastView
import com.thunderstorm.app.android.view.weather.QuickDetailView
import com.thunderstorm.app.android.viewmodel.WeatherViewModel
import com.thunderstorm.app.database.datastore.DataStore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun WeatherView(
    viewModel: WeatherViewModel,
    navController: NavController
) {
    viewModel.loadDefaultCity(navController.context)
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    
                },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_cities_icon),
                        contentDescription = stringResource(id = R.string.cities_icon_content_desc),
                        tint = MaterialTheme.colors.background,
                        modifier = Modifier.padding(10.dp)
                    )
                },
                shape = RoundedCornerShape(10.dp),
                backgroundColor = colorResource(id = R.color.thunderstorm_accent_color)
            )
        }
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
                modifier = Modifier.padding(top = 1.dp, start = 20.dp, bottom = 10.dp),
                fontSize = 14.sp
            )
            if (viewModel.forecastWeatherData.value != null) {
                val currentWeatherData = viewModel.forecastWeatherData.value!!
                val dataStore = DataStore(navController.context as ThunderstormBaseActivity)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    CurrentWeatherView(
                        weatherData = currentWeatherData,
                        currentWeatherIcon = viewModel.currentIconResource.value,
                        dataStore = dataStore
                    )
                    HourlyForecastView(
                        weatherData = currentWeatherData,
                        context = navController.context,
                        dataStore = dataStore
                    )
                    QuickDetailView(
                        weatherData = currentWeatherData,
                        dataStore = dataStore
                    )
                    DailyForecastView(
                        weatherData = currentWeatherData,
                        context = navController.context,
                        dataStore = dataStore
                    )
                    WeatherCreditFooter()
                }
            } else {
                LottieAnimation(
                    rememberLottieComposition(
                        spec = LottieCompositionSpec.RawRes(R.raw.loading_anim)
                    ).value,
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
fun WeatherCreditFooter() {
    Column(
        modifier = Modifier
            .height(80.dp)
            .padding(start = 20.dp, bottom = 5.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.weather_service_credit_text),
            style = MaterialTheme.typography.body2,
            fontSize = 16.sp
        )
        Text(
            text = String.format(
                stringResource(id = R.string.weather_last_updated_template),
                SimpleDateFormat("h:mm a", Locale.getDefault()).format(Date())
            ),
            color = LocalContentColor.current.copy(0.5F),
            fontSize = 14.sp
        )
    }
}