package com.thunderstorm.app.android.view.weather.alert

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.components.ActionBar
import com.thunderstorm.app.android.presentation.NavigationDestination
import com.thunderstorm.app.android.viewmodel.WeatherAlertViewModel
import com.thunderstorm.app.model.weather.special.AlertWeatherObject

@Composable
fun WeatherAlertList(
    navController: NavController,
    weatherCity: String,
    viewModel: WeatherAlertViewModel
) {
    viewModel.fetchAllAlerts(weatherCity)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ActionBar(
            text = stringResource(id = R.string.alerts_actionbar_title),
            backAction = {
                navController.navigate(NavigationDestination.WeatherView)
            }
        )
        LazyColumn(
            content = {
                itemsIndexed(viewModel.weatherAlertList.value) { index, item ->
                    item?.let {
                        AlertListItem(
                            alertObject = it,
                            navController = navController,
                            index = index
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun AlertListItem(
    alertObject: AlertWeatherObject,
    navController: NavController,
    index: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                colorResource(id = R.color.interface_gray)
                    .copy(0.5F)
            )
            .clickable {
                navController.navigate("""${NavigationDestination.AlertDetailView}/$index""")
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_warning_icon),
                contentDescription = stringResource(id = R.string.warning_icon_content_desc),
                modifier = Modifier
                    .padding(start = 20.dp)
                    .size(24.dp)
                    .align(Alignment.CenterVertically),
                tint = MaterialTheme.colors.onSurface
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 20.dp, top = 15.dp, bottom = 15.dp, end = 20.dp)
            ) {
                Text(
                    text = alertObject.headline,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = alertObject.event,
                    style = MaterialTheme.typography.body1
                )
            }
        }
        Row {
            Icon(
                painter = painterResource(id = R.drawable.ic_double_arrow_next),
                contentDescription = stringResource(id = R.string.double_arrow_next_content_desc),
                modifier = Modifier
                    .padding(end = 15.dp)
                    .size(24.dp)
            )
        }
    }
}