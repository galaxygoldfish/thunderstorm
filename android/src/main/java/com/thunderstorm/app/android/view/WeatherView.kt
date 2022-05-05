package com.thunderstorm.app.android.view

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.D
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.presentation.NavigationDestination
import com.thunderstorm.app.android.presentation.ThunderstormBaseActivity
import com.thunderstorm.app.android.utils.getViewModel
import com.thunderstorm.app.android.view.weather.*
import com.thunderstorm.app.android.view.weather.detail.AstronomyDetailCard
import com.thunderstorm.app.android.view.weather.detail.CurrentWeatherView
import com.thunderstorm.app.android.view.weather.detail.DailyForecastView
import com.thunderstorm.app.android.view.weather.detail.HourlyForecastView
import com.thunderstorm.app.android.viewmodel.WeatherViewModel
import com.thunderstorm.app.database.datastore.DataStore
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun WeatherView(
    navController: NavController,
    weatherCity: String? = null,
) {
    val viewModel = navController.context.getViewModel(WeatherViewModel::class.java)
    LaunchedEffect(true) {
        viewModel.apply {
            if (weatherCity == null) {
                loadDefaultCity(navController.context)
            } else {
                showWeatherData = false
                getCurrentData(navController.context, weatherCity)
            }
        }
    }
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(NavigationDestination.CityListView)
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
                backgroundColor = colorResource(id = R.color.thunderstorm_accent_color),
                modifier = Modifier.size(50.dp)
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    viewModel.apply {
                        Text(
                            text = currentCity?.cityName?.split(",")?.get(0).toString(),
                            modifier = Modifier
                                .padding(top = 20.dp, start = 20.dp)
                                .placeholder(
                                    visible = !viewModel.showWeatherData,
                                    shape = RoundedCornerShape(10.dp),
                                    highlight = PlaceholderHighlight.shimmer()
                                ),
                            style = MaterialTheme.typography.h3
                        )
                        Text(
                            text = currentCity?.stateName.toString(),
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier
                                .padding(top = 1.dp, start = 20.dp, bottom = 10.dp)
                                .placeholder(
                                    visible = !viewModel.showWeatherData,
                                    shape = RoundedCornerShape(10.dp),
                                    highlight = PlaceholderHighlight.shimmer()
                                ),
                            fontSize = 14.sp
                        )
                    }
                }
                Row(
                    modifier = Modifier.padding(top = 20.dp, end = 15.dp)
                ) {
                    if (viewModel.weatherAlertAvailable) {
                        IconButton(
                            onClick = {
                                navController.navigate(
                                    """${NavigationDestination.AlertView}/${viewModel.currentCity!!.serviceUrl}"""
                                )
                            },
                            content = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_warning_icon),
                                    contentDescription = stringResource(id = R.string.warning_icon_content_desc),
                                    modifier = Modifier
                                        .size(24.dp)
                                )
                            }
                        )
                    }
                    IconButton(
                        onClick = {
                            navController.navigate(NavigationDestination.SettingsView)
                        },
                        content = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_settings_icon),
                                contentDescription = stringResource(id = R.string.settings_icon_content_desc),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                val activity = navController.context as ThunderstormBaseActivity
                CurrentWeatherView(viewModel, activity)
                HourlyForecastView(viewModel, activity)
                AstronomyDetailCard(viewModel)
                QuickDetailView(viewModel, activity)
                DailyForecastView(viewModel, activity)
                WeatherCreditFooter()
            }
        }
    }
}

@Composable
fun WeatherCreditFooter() {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .height(80.dp)
            .padding(start = 20.dp, bottom = 5.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.weather_service_credit_text),
            style = MaterialTheme.typography.body2,
            fontSize = 16.sp,
            modifier = Modifier.clickable {
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse("https://weatherapi.com")
                    context.startActivity(this)
                }
            }
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