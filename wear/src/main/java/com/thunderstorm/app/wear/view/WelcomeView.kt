package com.thunderstorm.app.wear.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.thunderstorm.app.wear.R

@Composable
fun WelcomeView(navController: NavController) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.thunderstorm_cloud),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(start = 60.dp, end = 60.dp, top = 15.dp)
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.display3,
            color = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .padding(top = 5.dp)
                .align(Alignment.CenterHorizontally)
        )
        Button(
            onClick = {
            },
            content = @Composable {
                Text(
                    text = stringResource(id = R.string.welcome_view_start_button_text),
                    style = MaterialTheme.typography.button,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )

            },
            modifier = Modifier
                .padding(vertical = 20.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(30))
                .background(MaterialTheme.colors.primary)
        )
    }
}