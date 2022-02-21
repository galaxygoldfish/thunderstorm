package com.thunderstorm.app.wear.view

import android.app.RemoteInput
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.*
import androidx.wear.input.RemoteInputIntentHelper
import com.google.accompanist.pager.*
import com.thunderstorm.app.wear.NavigationDestination
import com.thunderstorm.app.wear.R
import com.thunderstorm.app.wear.utilities.getViewModel
import com.thunderstorm.app.wear.viewmodel.AddCityViewModel
import kotlinx.coroutines.launch

@Composable
@OptIn(ExperimentalPagerApi::class,
    ExperimentalWearMaterialApi::class)
fun AddCityView(navController: NavController) {
    val viewModel = navController.context.getViewModel(AddCityViewModel::class.java)
    val pagerState = rememberPagerState(0)
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        it.data?.let { data ->
            val results: Bundle = RemoteInput.getResultsFromIntent(data)
            viewModel.citySearchTextValue = results.getCharSequence("city-search").toString()
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            count = 2,
            state = pagerState,
            modifier = Modifier.fillMaxHeight(0.95F)
        ) { page ->
            when (page) {
                0 -> CitySearchView(pagerState, viewModel, launcher)
                1 -> CityResultView(viewModel, navController)
            }
        }
        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = MaterialTheme.colors.primary,
            inactiveColor = MaterialTheme.colors.primaryVariant,
            spacing = 5.dp,
            indicatorHeight = 5.dp,
            indicatorWidth = 5.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 2.dp)
        )
    }
}

@Composable
@OptIn(ExperimentalPagerApi::class,
    ExperimentalWearMaterialApi::class)
fun CitySearchView(
    pagerState: PagerState,
    viewModel: AddCityViewModel,
    launcher: ManagedActivityResultLauncher<Intent, ActivityResult>
) {
    Scaffold(
        timeText = {
            TimeText(timeTextStyle = MaterialTheme.typography.body1)
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.add_city_main_header_text),
                style = MaterialTheme.typography.display3,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(top = 35.dp)
            )
            Box(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth(0.85F)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colors.primaryVariant)
                    .clickable {
                        RemoteInputIntentHelper
                            .createActionRemoteInputIntent()
                            .apply {
                                val remoteInputs = listOf(
                                    RemoteInput
                                        .Builder("city-search")
                                        .build()
                                )
                                RemoteInputIntentHelper.putRemoteInputsExtra(this, remoteInputs)
                                launcher.launch(this)
                            }

                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search_glyph),
                        contentDescription = stringResource(id = R.string.ic_search_glyph_content_desc),
                        modifier = Modifier
                            .padding(10.dp)
                            .size(24.dp),
                        tint = MaterialTheme.colors.onSurface
                    )
                    Text(
                        text = viewModel.citySearchTextValue.ifEmpty {
                            stringResource(id = R.string.add_city_search_hint_text)
                        },
                        style = MaterialTheme.typography.body2
                    )
                }

            }
            rememberCoroutineScope().let { scope ->
                Button(
                    onClick = {
                        if (viewModel.citySearchTextValue.isNotEmpty()) {
                            scope.launch {
                                viewModel.reloadSearch()
                                pagerState.animateScrollToPage(1)
                            }
                        }
                    },
                    content = @Composable {
                        Text(
                            text = stringResource(id = R.string.add_city_search_result_button_text),
                            style = MaterialTheme.typography.button,
                            color = MaterialTheme.colors.onPrimary,
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )
                    },
                    modifier = Modifier
                        .padding(top = 15.dp, bottom = 20.dp)
                        .clip(RoundedCornerShape(30))
                        .background(
                            color = if (viewModel.citySearchTextValue.isEmpty()) {
                                MaterialTheme.colors.primary.copy(0.5F)
                            } else {
                                MaterialTheme.colors.primary
                            },
                            shape = RoundedCornerShape(30)
                        ),
                    colors = ButtonDefaults.buttonColors(Color.Transparent)
                )
            }
        }
    }
}

@Composable
fun CityResultView(
    viewModel: AddCityViewModel,
    navController: NavController
) {
    val lazyColumnState = rememberScalingLazyListState()
    Scaffold(
        vignette = {
            Vignette(vignettePosition = VignettePosition.Top)
        },
        positionIndicator = {
            PositionIndicator(scalingLazyListState = lazyColumnState)
        }
    ) {
        ScalingLazyColumn(
            state = lazyColumnState,
            modifier = Modifier.padding(end = 5.dp)
        ) {
            itemsIndexed(viewModel.cityResultList) { index, item ->
                Row(
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(15.dp))
                        .background(
                            color = MaterialTheme.colors.primaryVariant,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clickable {
                                   
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_gps_location),
                        contentDescription = stringResource(id = R.string.ic_gps_location_content_desc),
                        modifier = Modifier.padding(10.dp)
                    )
                    Column(
                        modifier = Modifier.padding(top = 5.dp, bottom = 7.dp, end = 7.dp)
                    ) {
                        Text(
                            text = item.name,
                            style = MaterialTheme.typography.body2
                        )
                        Text(
                            text = item.region,
                            style = MaterialTheme.typography.body1
                        )
                    }
                }
            }
        }
    }
}