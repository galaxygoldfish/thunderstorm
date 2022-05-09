package com.thunderstorm.app.android.view.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.thunderstorm.app.ThunderstormDatabase
import com.thunderstorm.app.android.NavigationDestination
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.viewmodel.SetupViewModel
import com.thunderstorm.app.database.DatabaseDriver
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.database.datastore.SharedContext
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun SetupViewPager(
     navController: NavController,
     viewModel: SetupViewModel
) {

    val viewPagerState = rememberPagerState(pageCount = 2)
    val composeAsync = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = viewPagerState,
            dragEnabled = false,
            modifier = Modifier.fillMaxHeight(0.9F),
            content = { page ->
                when (page) {
                    0 -> AddCityOnboarding(viewModel = viewModel)
                    1 -> CustomizationView(viewModel = viewModel)
                }
            }
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 30.dp)
                    .size(35.dp),
                onClick = {
                    if (viewPagerState.currentPage == 0) {
                        navController.navigate(NavigationDestination.WelcomeView)
                    } else {
                        composeAsync.launch {
                            viewPagerState.animateScrollToPage(viewPagerState.currentPage - 1)
                        }
                    }
                },
                content = @Composable {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_left_circle),
                        contentDescription = stringResource(id = R.string.left_circle_content_desc),
                        tint = MaterialTheme.colors.onBackground,
                    )
                }
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 30.dp)
                    .size(35.dp),
                onClick = {
                    if (viewModel.allowNavigateNext.value) {
                        if (viewPagerState.currentPage == 1) {
                            val dataStore =
                                DataStore(navController.context.applicationContext as SharedContext)
                            val cityDatabase =
                                ThunderstormDatabase(DatabaseDriver(navController.context).createDriver())
                            viewModel.apply {
                                selectedCity.value?.apply {
                                    cityDatabase.cityStoreQueries.insertNewCity(
                                        name,
                                        region,
                                        country,
                                        url!!
                                    )
                                }
                                dataStore.apply {
                                    putBoolean("INDICATION_ONBOARDING_DONE", true)
                                    putString("LAST_CITY_VIEWED", viewModel.selectedCity.value!!.url!!)
                                    navController.navigate(NavigationDestination.WeatherView)
                                }
                            }
                        } else {
                            composeAsync.launch {
                                viewPagerState.animateScrollToPage(viewPagerState.currentPage + 1)
                            }
                        }
                    }
                },
                content = @Composable {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_right_circle),
                        contentDescription = stringResource(id = R.string.right_circle_content_desc),
                        tint = if (viewModel.allowNavigateNext.value) {
                            MaterialTheme.colors.onBackground
                        } else {
                            MaterialTheme.colors.onBackground.copy(0.5F)
                        },
                    )
                }
            )
        }
    }
}