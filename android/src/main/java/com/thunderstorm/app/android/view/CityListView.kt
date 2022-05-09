package com.thunderstorm.app.android.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.thunderstorm.app.android.NavigationDestination
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.components.ActionBar
import com.thunderstorm.app.android.getIconForNameAndCode
import com.thunderstorm.app.android.viewmodel.CityListViewModel
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.database.datastore.SharedContext
import com.thunderstorm.app.model.SavedCityItem
import com.thunderstorm.app.model.weather.WeatherDataResult
import kotlin.math.roundToInt

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun CityListView(
    viewModel: CityListViewModel,
    navController: NavController
) {
    LaunchedEffect(true) {
        viewModel.loadSavedCities(navController.context)
    }
    Scaffold(
        topBar = {
            ActionBar(
                text = stringResource(id = R.string.city_list_actionbar_title),
                backAction = {
                    navController.popBackStack()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(NavigationDestination.CityAddView)
                },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search_glyph),
                        contentDescription = stringResource(id = R.string.search_glyph_content_desc),
                        tint = MaterialTheme.colors.background,
                        modifier = Modifier.padding(10.dp)
                    )
                },
                shape = RoundedCornerShape(10.dp),
                backgroundColor = colorResource(id = R.color.thunderstorm_accent_color),
                modifier = Modifier.size(50.dp)
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            content = {
                itemsIndexed(viewModel.savedCityList) { index, item ->
                    val dismissState = rememberDismissState(
                        confirmStateChange = {
                            if (it == DismissValue.DismissedToStart || it == DismissValue.DismissedToEnd) {
                                viewModel.removeCity(navController.context, index, item.serviceUrl)
                            }
                            true
                        }
                    )
                    SwipeToDismiss(
                        state = dismissState,
                        directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                        dismissThresholds = { direction ->
                            FractionalThreshold(if (direction == DismissDirection.StartToEnd) 0.25f else 0.5f)
                        },
                        background = {
                            val direction = dismissState.dismissDirection ?: return@SwipeToDismiss
                            val alignment = when (direction) {
                                DismissDirection.StartToEnd -> Alignment.CenterStart
                                DismissDirection.EndToStart -> Alignment.CenterEnd
                            }
                            Box(
                                Modifier.fillMaxSize().padding(horizontal = 40.dp),
                                contentAlignment = alignment
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_trash_can),
                                    contentDescription = null,
                                    tint = MaterialTheme.colors.onBackground
                                )
                            }
                        },
                        dismissContent = {
                            CityListItem(
                                cityDetails = item,
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                    )
                    
                }
            }
        )
    }
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun CityListItem(
    cityDetails: SavedCityItem,
    viewModel: CityListViewModel,
    navController: NavController
) {
    val dataStore = DataStore(context = navController.context.applicationContext as SharedContext)
    var cityIcon by remember { mutableStateOf<Int?>(null) }
    var weatherResponse by remember { mutableStateOf<WeatherDataResult?>(null) }
    LaunchedEffect(true) {
        viewModel.fetchDataForCard(cityDetails.serviceUrl).let {
            weatherResponse = it
            cityIcon = navController.context.getIconForNameAndCode(
                it.current.isDay,
                it.current.condition.code
            )
        }
    }
    Card(
        onClick = {
            navController.navigate(
                "${NavigationDestination.WeatherView}/${cityDetails.serviceUrl}"
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 145.dp)
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
            .placeholder(
                visible = weatherResponse == null,
                shape = RoundedCornerShape(10.dp),
                highlight = PlaceholderHighlight.shimmer()
            ),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = MaterialTheme.colors.secondary,
        border = BorderStroke(3.dp, MaterialTheme.colors.primaryVariant)

    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(top = 10.dp, start = 15.dp)
            ) {
                Text(
                    text = cityDetails.cityName,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.padding(end = 20.dp)
                )
                Text(
                    text = cityDetails.stateName
                )
                weatherResponse?.current?.apply {
                    Text(
                        text = String.format(
                            stringResource(id = R.string.weather_temperature_template),
                            if (dataStore.getBoolean("USE_IMPERIAL_UNITS")) {
                                tempFarenheit.roundToInt()
                            } else {
                                tempCelsius.roundToInt()
                            }
                        ),
                        style = MaterialTheme.typography.h4,
                        fontSize = 40.sp,
                        modifier = Modifier.padding(top = 5.dp, bottom = 10.dp)
                    )
                }
            }
            cityIcon?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 15.dp, top = 10.dp, bottom = 10.dp)
                        .size(110.dp)
                )
            }
        }
    }
}