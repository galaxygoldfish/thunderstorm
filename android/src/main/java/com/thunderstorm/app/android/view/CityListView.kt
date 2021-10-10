package com.thunderstorm.app.android.view

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.components.ActionBar
import com.thunderstorm.app.android.presentation.ThunderstormBaseActivity
import com.thunderstorm.app.android.utils.getIconForNameAndCode
import com.thunderstorm.app.android.viewmodel.CityListViewModel
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.database.datastore.DataStoreName
import com.thunderstorm.app.model.SavedCityItem
import com.thunderstorm.app.networking.NetworkingClient
import kotlin.math.roundToInt

@ExperimentalPagerApi
@ExperimentalMaterialApi
@Composable
fun CityListView(
    viewModel: CityListViewModel,
    navController: NavController
) {
    viewModel.loadSavedCities(navController.context)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ActionBar(
            text = stringResource(id = R.string.city_list_actionbar_title),
            backAction = {
                val listValueTemp = viewModel.cityList.value
                listValueTemp.clear()
                viewModel.cityList.value = listValueTemp
                navController.popBackStack()
            }
        )
        LazyColumn(
            content = {
                itemsIndexed(viewModel.cityList.value) { _, item ->
                    CityListItem(
                        cityDetails = item,
                        context = navController.context
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
    context: Context
) {
    val dataStore = DataStore(context = context as ThunderstormBaseActivity)
    val cityIcon = context.getIconForNameAndCode(
        cityDetails.weatherResponse.current.isDay,
        cityDetails.weatherResponse.current.condition.code
    )
    Card(
        onClick = {

        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = colorResource(id = R.color.interface_gray)
            .copy(0.5F)
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
                )
                Text(
                    text = cityDetails.stateName
                )
                cityDetails.weatherResponse.current.apply {
                    Text(
                        text = String.format(
                            stringResource(id = R.string.weather_temperature_template),
                            if (dataStore.getInteger("PREF_TEMP_UNITS") == 0) {
                                tempCelsius.roundToInt()
                            } else {
                                tempFarenheit.roundToInt()
                            }
                        ),
                        style = MaterialTheme.typography.h4,
                        fontSize = 40.sp,
                        modifier = Modifier.padding(top = 5.dp, bottom = 10.dp)
                    )
                }
            }
            Image(
                painter = painterResource(id = cityIcon),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 15.dp, top = 10.dp, bottom = 10.dp)
                    .size(110.dp)
            )
        }
    }
}