package com.thunderstorm.app.android.view.onboarding

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.viewmodel.SetupViewModel
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun CustomizationView(
    viewModel: SetupViewModel,
    sheetState: ModalBottomSheetState
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_switch_double),
            contentDescription = stringResource(id = R.string.maps_location_place_content_desc),
            modifier = Modifier
                .padding(top = 25.dp, start = 20.dp)
                .size(40.dp),
            tint = MaterialTheme.colors.onBackground
        )
        Text(
            text = stringResource(id = R.string.customization_header),
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(top = 5.dp, start = 20.dp, end = 50.dp),
            color = MaterialTheme.colors.onBackground
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .scrollable(rememberScrollState(), Orientation.Vertical)
        ) {
            CustomizationPreference(
                title = stringResource(id = R.string.customization_temp_title),
                availableOptions = stringArrayResource(id = R.array.temperature_units),
                selectionManager = viewModel.selectionTemperature,
                sheetState = sheetState,
                viewModel = viewModel
            )
            CustomizationPreference(
                title = stringResource(id = R.string.customization_speed_title),
                availableOptions = stringArrayResource(id = R.array.speed_units),
                selectionManager = viewModel.selectionSpeed,
                sheetState = sheetState,
                viewModel = viewModel
            )
            CustomizationPreference(
                title = stringResource(id = R.string.customization_precipitation_title),
                availableOptions = stringArrayResource(id = R.array.precipitation_units),
                selectionManager = viewModel.selectionPrecip,
                sheetState = sheetState,
                viewModel = viewModel
            )
            CustomizationPreference(
                title = stringResource(id = R.string.customization_pressure_title),
                availableOptions = stringArrayResource(id = R.array.pressure_units),
                selectionManager = viewModel.selectionAir,
                sheetState = sheetState,
                viewModel = viewModel
            )
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun CustomizationPreference(
    title: String,
    availableOptions: Array<String>,
    selectionManager: MutableState<Int>,
    sheetState: ModalBottomSheetState,
    viewModel: SetupViewModel
) {

    val composeAsync = rememberCoroutineScope()

    Box(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(bottom = 12.dp),
            onClick = {
                viewModel.currentItemSelection.value = selectionManager
                viewModel.currentSelectionItems.value = availableOptions
                composeAsync.launch {
                    sheetState.show()
                }
            },
            shape = RoundedCornerShape(10.dp),
            backgroundColor = colorResource(id = R.color.interface_gray_alt)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 13.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
                Text(
                    text = availableOptions[selectionManager.value],
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface.copy(0.6F),
                    fontSize = 16.sp
                )
            }
        }
    }
}