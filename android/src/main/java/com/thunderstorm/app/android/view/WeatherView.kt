package com.thunderstorm.app.android.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thunderstorm.app.android.viewmodel.WeatherViewModel

@Composable
fun WeatherView(
    viewModel: WeatherViewModel,
    navController: NavController
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        viewModel.currentCityName.value?.let { name ->
            Text(
                text = name,
                modifier = Modifier.padding(top = 20.dp, start = 20.dp)
            )
        }
    }
}