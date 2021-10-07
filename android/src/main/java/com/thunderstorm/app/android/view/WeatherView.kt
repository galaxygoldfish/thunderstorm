package com.thunderstorm.app.android.view

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.presentation.NavigationDestination
import com.thunderstorm.app.android.presentation.ThunderstormBaseActivity
import com.thunderstorm.app.android.view.weather.*
import com.thunderstorm.app.android.view.weather.detail.AstronomyDetailCard
import com.thunderstorm.app.android.view.weather.detail.CurrentWeatherView
import com.thunderstorm.app.android.view.weather.detail.DailyForecastView
import com.thunderstorm.app.android.view.weather.detail.HourlyForecastView
import com.thunderstorm.app.android.viewmodel.WeatherViewModel
import com.thunderstorm.app.database.datastore.DataStore
import com.valentinilk.shimmer.shimmer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@ExperimentalAnimationApi
@ExperimentalFoundationApi
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
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
                }
                Row(
                    modifier = Modifier.padding(top = 20.dp, end = 15.dp)
                ) {
                    if (viewModel.weatherAlertAvailable.value) {
                        IconButton(
                            onClick = {
                                navController.navigate(
                                    """${NavigationDestination.AlertView}/${viewModel.currentCityServiceUrl.value}"""
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
            val dataStore = DataStore(navController.context as ThunderstormBaseActivity)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                CurrentWeatherView(
                    viewModel = viewModel,
                    dataStore = dataStore
                )
                HourlyForecastView(
                    viewModel = viewModel,
                    context = navController.context,
                    dataStore = dataStore
                )
                AstronomyDetailCard(
                    viewModel = viewModel //!
                )
                QuickDetailView(
                    viewModel = viewModel,
                    dataStore = dataStore
                )
                DailyForecastView(
                    viewModel = viewModel,
                    context = navController.context,
                    dataStore = dataStore
                )
                WeatherCreditFooter()
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun WeatherLoadingView() {
    val commonBackground = Color.Gray.copy(0.4F)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .shimmer()
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 40.dp, start = 20.dp)
                    .size(height = 100.dp, width = 120.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(commonBackground)
            )
            Box(
                modifier = Modifier
                    .padding(top = 40.dp, end = 20.dp)
                    .size(height = 110.dp, width = 120.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(commonBackground)
            )
        }
        Box(
            modifier = Modifier
                .padding(top = 5.dp, start = 20.dp)
                .clip(RoundedCornerShape(10.dp))
                .size(height = 20.dp, width = 90.dp)
                .background(commonBackground)
        )
        Box(
            modifier = Modifier
                .padding(top = 5.dp, start = 20.dp)
                .clip(RoundedCornerShape(10.dp))
                .size(height = 20.dp, width = 110.dp)
                .background(commonBackground)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 35.dp, start = 20.dp, end = 20.dp)
        ) {
            repeat(4) {
                Box(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .size(height = 125.dp, width = 65.dp)
                        .background(commonBackground)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(start = 20.dp, end = 20.dp, top = 25.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(commonBackground)
        )
        LazyVerticalGrid(
            cells = GridCells.Fixed(2),
            content = {
                items(4) {
                    Box(
                        modifier = Modifier
                            .height(100.dp)
                            .padding(5.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(commonBackground)
                    )
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
        )
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