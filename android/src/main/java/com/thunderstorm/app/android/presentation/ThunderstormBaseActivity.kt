package com.thunderstorm.app.android.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.thunderstorm.app.android.theme.ThunderstormTheme
import com.thunderstorm.app.android.view.SettingsView
import com.thunderstorm.app.android.view.WeatherView
import com.thunderstorm.app.android.view.onboarding.SetupViewPager
import com.thunderstorm.app.android.view.WelcomeView
import com.thunderstorm.app.android.view.weather.alert.WeatherAlertDetail
import com.thunderstorm.app.android.view.weather.alert.WeatherAlertList
import com.thunderstorm.app.android.viewmodel.SettingsViewModel
import com.thunderstorm.app.android.viewmodel.SetupViewModel
import com.thunderstorm.app.android.viewmodel.WeatherAlertViewModel
import com.thunderstorm.app.android.viewmodel.WeatherViewModel
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.model.weather.WeatherDataResult

object NavigationDestination {
    const val WelcomeView = "welcome"
    const val SetupView = "setup"
    const val WeatherView = "weather"
    const val AlertView = "alert"
    const val AlertDetailView = "alertdetails"
    const val SettingsView = "settings"
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
class ThunderstormBaseActivity : ComponentActivity() {

    private lateinit var navigationController: NavHostController

    private val setupViewModel: SetupViewModel by viewModels()
    private val weatherViewModel: WeatherViewModel by viewModels()
    private val weatherAlertViewModel: WeatherAlertViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        setContent {
            ThunderstormTheme {
                ThunderstormNavHost()
            }
        }
    }

    @Composable
    fun ThunderstormNavHost() {

        val dataStore = DataStore(this)
        val passedSetup = dataStore.getBoolean("INDICATION_ONBOARDING_DONE")

        navigationController = rememberNavController()

        NavHost(
            navController = navigationController,
            startDestination = if (passedSetup) {
                NavigationDestination.WeatherView
            } else {
                NavigationDestination.WelcomeView
            },
            builder = {
                composable(NavigationDestination.WelcomeView) {
                    WelcomeView(
                        navController = navigationController
                    )
                }
                composable(NavigationDestination.SetupView) {
                    SetupViewPager(
                        navController = navigationController,
                        viewModel = setupViewModel
                    )
                }
                composable(NavigationDestination.WeatherView) {
                    WeatherView(
                        viewModel = weatherViewModel,
                        navController = navigationController
                    )
                }
                composable("""${NavigationDestination.AlertView}/{weatherCity}""") {
                    WeatherAlertList(
                        navController = navigationController,
                        weatherCity = it.arguments!!.getString("weatherCity")!!,
                        viewModel = weatherAlertViewModel
                    )
                }
                composable("""${NavigationDestination.AlertDetailView}/{alertIndex}""") {
                    WeatherAlertDetail(
                        navController = navigationController,
                        alertIndex = it.arguments!!.getInt("alertIndex"),
                        viewModel = weatherAlertViewModel
                    )
                }
                composable(NavigationDestination.SettingsView) {
                    SettingsView(
                        navController = navigationController,
                        viewModel = settingsViewModel
                    )
                }
            }
        )
    }

}