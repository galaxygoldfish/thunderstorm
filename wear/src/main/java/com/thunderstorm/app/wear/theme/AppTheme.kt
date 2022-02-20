package com.thunderstorm.app.android.theme

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.MaterialTheme
import com.thunderstorm.app.wear.theme.colors
import com.thunderstorm.app.wear.theme.typography

@Composable
fun ThunderstormTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = colors(),
        typography = typography(),
        content = content
    )
}