package com.thunderstorm.app.android.view.onboarding

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.components.BasePreference
import com.thunderstorm.app.android.viewmodel.SetupViewModel
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.database.datastore.SharedContext

@ExperimentalMaterialApi
@Composable
fun CustomizationView(
    viewModel: SetupViewModel
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
            val dataStore = DataStore(LocalContext.current.applicationContext as SharedContext)
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
        }
    }
}