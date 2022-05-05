package com.thunderstorm.app.android.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.airbnb.lottie.LottieAnimationView
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.components.InputFieldWithHint
import com.thunderstorm.app.android.utils.getViewModel
import com.thunderstorm.app.android.view.onboarding.CityResultListItem
import com.thunderstorm.app.android.viewmodel.CityAddViewModel

@OptIn(ExperimentalMaterialApi::class,
    ExperimentalAnimationApi::class)
@Composable
fun CityAddView(navController: NavController) {
    val viewModel = LocalContext.current.getViewModel(CityAddViewModel::class.java)
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back_icon),
                        contentDescription = stringResource(id = R.string.back_button_content_desc),
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colors.onSurface
                    )
                },
                modifier = Modifier.padding(start = 5.dp, end = 5.dp, top = 20.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, end = 20.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorResource(id = R.color.interface_gray_alt))
                    .border(
                        border = BorderStroke(3.dp, colorResource(id = R.color.interface_gray)),
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search_glyph),
                    contentDescription = stringResource(id = R.string.search_glyph_content_desc),
                    modifier = Modifier
                        .padding(start = 15.dp)
                        .size(25.dp)
                        .align(Alignment.CenterVertically),
                    tint = MaterialTheme.colors.onBackground
                )
                InputFieldWithHint(
                    hint = stringResource(id = R.string.add_location_search_field_hint),
                    style = MaterialTheme.typography.body1,
                    textFieldValue = viewModel.currentSearchQuery,
                    onValueChange = { value ->
                        viewModel.currentSearchQuery = value
                        if (value.text.isNotEmpty()) {
                            viewModel.searchCities()
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                        .padding(start = 6.dp)
                )
            }
        }
        AnimatedVisibility(viewModel.citySearchResult.size == 0) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.explore_world_person),
                    contentDescription = stringResource(id = R.string.explore_world_content_desc),
                    modifier = Modifier
                        .fillMaxSize(0.7F)
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp)
                )
                Text(
                    text = stringResource(id = R.string.city_add_placeholder_text),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
        AnimatedVisibility(viewModel.citySearchResult.size > 1) {
            LazyColumn(
                content = {
                    itemsIndexed(viewModel.citySearchResult) { _, searchResult ->
                        CityResultListItem(
                            searchResult = searchResult,
                            viewModel = viewModel
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
        }
    }
    if (viewModel.showDoneDialog) {
        AddCityDialog(viewModel = viewModel)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AddCityDialog(viewModel: CityAddViewModel) {
    val currentCityCached = viewModel.currentSelectedCity!!
    Dialog(onDismissRequest = { viewModel.showDoneDialog = false }) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(
                    if (MaterialTheme.colors.isLight) {
                        MaterialTheme.colors.background
                    } else {
                        colorResource(id = R.color.interface_gray_alt)
                    }
                )
                .fillMaxWidth(1.0F)
                .border(
                    border = BorderStroke(3.dp, colorResource(id = R.color.interface_gray)),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Text(
                text = String.format(
                    stringResource(id = R.string.city_add_dialog_title_template),
                    currentCityCached.name.split(",")[0]
                ),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(start = 20.dp, top = 15.dp, end = 15.dp)
            )
            Text(
                text = stringResource(id = R.string.city_add_dialog_subtitle),
                modifier = Modifier.padding(start = 20.dp, top = 5.dp, end = 15.dp)
            )
            Row {
                val context = LocalContext.current
                Button(
                    onClick = {
                        viewModel.saveCity(viewModel.currentSelectedCity!!, context)
                    },
                    modifier = Modifier
                        .padding(20.dp)
                        .clip(RoundedCornerShape(80.dp))
                        .background(MaterialTheme.colors.primary)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.city_add_dialog_confirm),
                            color = MaterialTheme.colors.background
                        )
                        AnimatedVisibility(visible = viewModel.saveCityInProgress) {
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .size(12.dp),
                                color = MaterialTheme.colors.background,
                                strokeWidth = 1.dp
                            )
                        }
                    }
                }
                Button(
                    onClick = {
                        viewModel.showDoneDialog = false
                    },
                    modifier = Modifier
                        .padding(bottom = 20.dp, top = 20.dp)
                        .clip(RoundedCornerShape(50.dp)),
                    colors = ButtonDefaults
                        .buttonColors(backgroundColor = if (MaterialTheme.colors.isLight) {
                            MaterialTheme.colors.background
                        } else {
                            colorResource(id = R.color.interface_gray_alt)
                        })
                ) {
                    Text(
                        text = stringResource(id = R.string.city_add_dialog_cancel),
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }
}