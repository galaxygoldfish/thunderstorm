package com.thunderstorm.app.android.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.presentation.NavigationDestination

@Composable
fun WelcomeView(
    navController: NavController
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(
                modifier = Modifier.fillMaxHeight(0.2F)
            )
            Image(
                painter = painterResource(id = R.drawable.thunderstorm_cloud),
                contentDescription = stringResource(id = R.string.sun_rain_lightning_cloud_content_desc),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 60.dp)
            )
            Text(
                text = stringResource(id = R.string.welcome_title_header),
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = stringResource(id = R.string.welcome_title_subheader),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(
                modifier = Modifier.fillMaxHeight(0.3F)
            )
            Button(
                onClick = {
                    navController.navigate(NavigationDestination.SetupView)
                },
                content = @Composable {
                    Text(
                        text = stringResource(id = R.string.welcome_button_start_text),
                        style = MaterialTheme.typography.overline,
                        color = MaterialTheme.colors.onPrimary
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_double_arrow_next),
                        contentDescription = stringResource(id = R.string.double_arrow_next_content_desc),
                        modifier = Modifier
                            .size(30.dp)
                            .padding(start = 10.dp),
                        tint = MaterialTheme.colors.onPrimary
                    )
                },
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(50))
                    .background(colorResource(id = R.color.thunderstorm_accent_color))
            )
            Spacer(
                modifier = Modifier.fillMaxHeight(0.3F)
            )
        }
    }
}