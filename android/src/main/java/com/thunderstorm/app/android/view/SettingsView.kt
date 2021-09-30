package com.thunderstorm.app.android.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.components.ActionBar
import com.thunderstorm.app.android.presentation.NavigationDestination
import com.thunderstorm.app.android.viewmodel.SettingsViewModel

@Composable
fun SettingsView(
    navController: NavController,
    viewModel: SettingsViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ActionBar(
            text = stringResource(id = R.string.settings_actionbar_title),
            backAction = {
                navController.navigate(NavigationDestination.WeatherView)
            }
        )
    }
}