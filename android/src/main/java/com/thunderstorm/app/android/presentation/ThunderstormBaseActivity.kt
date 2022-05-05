package com.thunderstorm.app.android.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.thunderstorm.app.android.theme.ThunderstormTheme
import com.thunderstorm.app.android.view.onboarding.SetupViewPager
import com.thunderstorm.app.android.view.weather.alert.WeatherAlertDetail
import com.thunderstorm.app.android.view.weather.alert.WeatherAlertList
import com.thunderstorm.app.android.view.*
import com.thunderstorm.app.android.viewmodel.*
import com.thunderstorm.app.database.datastore.DataStore

object NavigationDestination {
    const val WelcomeView = "welcome"
    const val SetupView = "setup"
    const val WeatherView = "weather"
    const val AlertView = "alert"
    const val AlertDetailView = "alertdetails"
    const val SettingsView = "settings"
    const val CityListView = "cities"
    const val CityAddView = "cityadd"
}

@ExperimentalPagerApi
@ExperimentalMaterialApi
class ThunderstormBaseActivity : ComponentActivity() {

    private lateinit var navigationController: NavHostController

    private val setupViewModel: SetupViewModel by viewModels()
    private val weatherAlertViewModel: WeatherAlertViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val cityListViewModel: CityListViewModel by viewModels()

    @OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        setContent {
            ThunderstormTheme {
                ThunderstormNavHost()
            }
        }
    }

    @ExperimentalAnimationApi
    @ExperimentalFoundationApi
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
                        navController = navigationController
                    )
                }
                composable("${NavigationDestination.WeatherView}/{weatherCity}/{displayCity}/{displayRegion}") {
                    it.arguments!!.apply {
                        WeatherView(
                            navController = navigationController,
                            weatherCity = getString("weatherCity")
                        )
                    }
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
                composable(NavigationDestination.CityListView) {
                    CityListView(
                        viewModel = cityListViewModel,
                        navController = navigationController
                    )
                }
                composable(NavigationDestination.CityAddView) {
                    CityAddView(navController = navigationController)
                }
            }
        )
    }

}