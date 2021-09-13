package com.thunderstorm.app.android.view.onboarding

import android.graphics.Color as ColorGraphics
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.thunderstorm.app.ThunderstormDatabase
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.presentation.NavigationDestination
import com.thunderstorm.app.android.presentation.ThunderstormBaseActivity
import com.thunderstorm.app.android.viewmodel.SetupViewModel
import com.thunderstorm.app.database.DatabaseDriver
import com.thunderstorm.app.database.datastore.DataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun SetupViewPager(
     navController: NavController,
     viewModel: SetupViewModel
) {

    val viewPagerState = rememberPagerState(pageCount = 2)
    val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val composeAsync = rememberCoroutineScope()

    (navController.context as ThunderstormBaseActivity).apply {
        if (bottomSheetState.isVisible) {
            window.navigationBarColor = resources.getColor(R.color.interface_gray)
        } else {
            window.navigationBarColor = ColorGraphics.parseColor("#00000000")
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        modifier = Modifier.fillMaxSize(),
        scrimColor = MaterialTheme.colors.background.copy(0.5F),
        sheetContent = @Composable {
            Column(
                modifier = Modifier.padding(bottom = 10.dp, top = 20.dp)
            ) {
                viewModel.currentSelectionItems.value.forEachIndexed { index, value ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (viewModel.currentItemSelection.value.value == index) {
                                    Color.DarkGray
                                } else {
                                    colorResource(id = R.color.interface_gray_contrast)
                                })
                            .clickable {
                                viewModel.currentItemSelection.value.value = index
                            },
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = value,
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.align(Alignment.CenterVertically)
                                .padding(vertical = 15.dp)
                        )
                    }
                }
            }
        },
        sheetBackgroundColor = colorResource(id = R.color.interface_gray),
        sheetShape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalPager(
                state = viewPagerState,
                dragEnabled = false,
                modifier = Modifier.fillMaxHeight(0.9F),
                content = { page ->
                    when (page) {
                        0 -> AddCityView(viewModel = viewModel)
                        1 -> CustomizationView(viewModel = viewModel, sheetState = bottomSheetState)
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
                                val dataStore = DataStore(navController.context as ThunderstormBaseActivity)
                                val cityDatabase = ThunderstormDatabase(DatabaseDriver(navController.context).createDriver())
                                viewModel.apply {
                                    selectedCity.value?.apply {
                                        cityDatabase.cityStoreQueries.insertNewCity(name, region, country, url)
                                    }
                                    dataStore.apply {
                                        putInteger("PREF_TEMP_UNITS", selectionTemperature.value)
                                        putInteger("PREF_SPEED_UNITS", selectionSpeed.value)
                                        putInteger("PREF_PRECIP_UNITS", selectionPrecip.value)
                                        putInteger("PREF_AIR_UNITS", selectionAir.value)
                                        putBoolean("INDICATION_ONBOARDING_DONE", true)
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
}