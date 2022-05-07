package com.thunderstorm.app.android.view.onboarding

import android.util.Log
import androidx.activity.ComponentActivity
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
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.view.settings.BasePreference
import com.thunderstorm.app.android.viewmodel.SetupViewModel
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.database.datastore.SharedContext
import kotlinx.coroutines.launch

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