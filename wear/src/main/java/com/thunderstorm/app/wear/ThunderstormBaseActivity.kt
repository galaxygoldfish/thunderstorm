package com.thunderstorm.app.wear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.composable
import androidx.wear.compose.navigation.rememberSwipeDismissableNavController
import com.thunderstorm.app.android.theme.ThunderstormTheme
import com.thunderstorm.app.wear.view.WelcomeView


object NavigationDestination {
    const val WelcomeView = "welcome"
}

@OptIn(ExperimentalWearMaterialApi::class)
class ThunderstormBaseActivity : ComponentActivity() {

    lateinit var navigationController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThunderstormTheme {
                ThunderstormNavHost()
            }
        }
    }

    @ExperimentalWearMaterialApi
    @Composable
    fun ThunderstormNavHost() {
        navigationController = rememberSwipeDismissableNavController()
        SwipeDismissableNavHost(
            navController = navigationController,
            startDestination = NavigationDestination.WelcomeView,
        ) {
            composable(NavigationDestination.WelcomeView) {
                WelcomeView(navController = navigationController)
            }
        }
    }


}