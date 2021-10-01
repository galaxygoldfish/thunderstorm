package com.thunderstorm.app.android.view.weather.alert

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.components.ActionBar
import com.thunderstorm.app.android.presentation.NavigationDestination
import com.thunderstorm.app.android.viewmodel.WeatherAlertViewModel

@Composable
fun WeatherAlertDetail(
    navController: NavController,
    alertIndex: Int,
    viewModel: WeatherAlertViewModel
) {
    val currentAlert = viewModel.weatherAlertList.value[alertIndex]!!
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ActionBar(
            text = stringResource(id = R.string.alerts_detail_page_title),
            backAction = {
                navController.navigate("""${NavigationDestination.AlertView}/${viewModel.currentCity.value}""")
            }
        )
        val alertHeaderItems = listOf(
            stringResource(id = R.string.weather_alert_headline_header), stringResource(id = R.string.weather_alert_severity_header),
            stringResource(id = R.string.weather_alert_urgency_header), stringResource(id = R.string.weather_alert_areas_header),
            stringResource(id = R.string.weather_alert_category_header), stringResource(id = R.string.weather_alert_event_header),
            stringResource(id = R.string.weather_alert_note_header), stringResource(id = R.string.weather_alert_desc_header),
            stringResource(id = R.string.weather_alert_instruction_header)
        )
        val alertValueItems = listOf(
            currentAlert.headline, currentAlert.severity, currentAlert.urgency, currentAlert.areas,
            currentAlert.category, currentAlert.event, currentAlert.note, currentAlert.desc,
            currentAlert.instruction
        )
        alertHeaderItems.forEachIndexed { index, item ->
            if (alertValueItems[index].isNotEmpty()) {
                Column(
                    modifier = Modifier.padding(bottom = 15.dp, end = 20.dp)
                ) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.overline,
                        modifier = Modifier.padding(start = 20.dp),
                        color = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = alertValueItems[index],
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }
            }
        }

    }
}