package com.thunderstorm.app.android.view.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.components.InputFieldWithHint
import com.thunderstorm.app.android.viewmodel.SetupViewModel
import com.thunderstorm.app.model.SearchCityResult

@ExperimentalMaterialApi
@Composable
fun AddCityView(
    viewModel: SetupViewModel
) {
    Column {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_maps_location_place),
                contentDescription = stringResource(id = R.string.maps_location_place_content_desc),
                modifier = Modifier
                    .padding(top = 25.dp, start = 20.dp)
                    .size(40.dp),
                tint = MaterialTheme.colors.onBackground
            )
            Text(
                text = stringResource(id = R.string.add_location_header_title),
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(top = 5.dp, start = 20.dp, end = 50.dp),
                color = MaterialTheme.colors.onBackground
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .height(50.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(colorResource(id = R.color.interface_gray))
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
                    textFieldValue = viewModel.searchFieldValue.value,
                    onValueChange = { value ->
                        viewModel.searchFieldValue.value = value
                        if (value.text.isNotEmpty()) {
                            viewModel.fetchCitiesForSearch()
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .fillMaxWidth()
                        .padding(start = 6.dp)
                )
            }
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                if (viewModel.cityAutocompleteItems.value.size == 0) {
                    viewModel.selectedCity.value = null
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.nothing_here_person),
                            contentDescription = stringResource(id = R.string.nothing_here_content_desc),
                            modifier = Modifier
                                .fillMaxSize(0.7F)
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 20.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.add_location_nothing_here_text),
                            style = MaterialTheme.typography.body1,
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                } else {
                    LazyColumn(
                        content = {
                            itemsIndexed(viewModel.cityAutocompleteItems.value) { _, searchResult ->
                                CityResultListItem(
                                    searchResult = searchResult,
                                    selectedCardState = viewModel.selectedCity,
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
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CityResultListItem(
    searchResult: SearchCityResult,
    selectedCardState: MutableState<SearchCityResult?>,
    viewModel: SetupViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp),
        shape = RoundedCornerShape(10.dp),
        backgroundColor = colorResource(id = R.color.interface_gray_alt),
        elevation = 0.dp,
        onClick = {
            selectedCardState.value = searchResult
            viewModel.allowNavigateNext.value = true
        }
    ) {
        Box (modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterStart),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_gps_location),
                    contentDescription = stringResource(id = R.string.gps_location_content_desc),
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(start = 15.dp),
                    tint = MaterialTheme.colors.onBackground
                )
                Column {
                    Text(
                        text = searchResult.name.split(",")[0],
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier.padding(start = 15.dp, top = 5.dp),
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = searchResult.region,
                        style = MaterialTheme.typography.body2,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 15.dp, top = 1.dp),
                        color = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = searchResult.country,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.padding(start = 15.dp, bottom = 10.dp),
                        fontSize = 14.sp,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
            if (selectedCardState.value != null && selectedCardState.value == searchResult) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_check_circle),
                    contentDescription = stringResource(id = R.string.check_circle_content_desc),
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 15.dp),
                    tint = colorResource(id = R.color.affirmative_green)
                )
            }
        }
    }
}