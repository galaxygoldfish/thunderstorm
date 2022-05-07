package com.thunderstorm.app.android.view

import android.preference.SwitchPreference
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.components.ActionBar
import com.thunderstorm.app.android.presentation.NavigationDestination
import com.thunderstorm.app.android.view.settings.BasePreference
import com.thunderstorm.app.android.viewmodel.SettingsViewModel
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.database.datastore.SharedContext

@Composable
fun SettingsView(
    navController: NavController,
    viewModel: SettingsViewModel
) {
    val dataStore = DataStore(navController.context.applicationContext as SharedContext)
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ActionBar(
            text = stringResource(id = R.string.settings_actionbar_title),
            backAction = {
                navController.navigate(NavigationDestination.WeatherView)
            }
        )
        LazyColumn {
            item {
                val switchState = remember { mutableStateOf(dataStore.getBoolean("USE_IMPERIAL_UNITS")) }
                BasePreference(
                    title = stringResource(id = R.string.settings_imperial_title),
                    subtitle = stringResource(id = R.string.settings_imperial_subtitle),
                    icon = painterResource(id = R.drawable.ic_world_icon),
                    switchPreference = true,
                    onClick = {
                        dataStore.putBoolean("USE_IMPERIAL_UNITS", switchState.value)
                    },
                    checkState = switchState
                )
                BasePreference(
                    title = stringResource(id = R.string.settings_widget_manager_title),
                    subtitle = stringResource(id = R.string.settings_widget_manager_subtitle),
                    icon = painterResource(id = R.drawable.ic_file_edit),
                    onClick = {
                        navController.navigate(NavigationDestination.WidgetManageView)
                    }
                )
            }
        }
    }
}