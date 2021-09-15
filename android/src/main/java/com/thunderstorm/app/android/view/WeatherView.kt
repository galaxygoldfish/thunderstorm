package com.thunderstorm.app.android.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.thunderstorm.app.android.viewmodel.WeatherViewModel
import kotlin.math.roundToInt

@Composable
fun WeatherView(
    viewModel: WeatherViewModel,
    navController: NavController
) {
    viewModel.loadDefaultCity(navController.context)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        viewModel.currentCityName.value?.let { name ->
            Text(
                text = name,
                modifier = Modifier.padding(top = 20.dp, start = 20.dp),
                style = MaterialTheme.typography.h3
            )
            Text(
                text = viewModel.currentRegionName.value!!,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 1.dp, start = 20.dp),
                fontSize = 14.sp
            )
            if (viewModel.currentWeatherData.value != null) {
                viewModel.currentWeatherData.value?.apply {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 30.dp)
                    ) {
                        Text(
                            text = current.tempFarenheit.roundToInt().toString(),
                            style = MaterialTheme.typography.h1,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    }
                }
            } else {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = LocalContentColor.current
                )
            }
        }
    }
}