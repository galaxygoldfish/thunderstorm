package com.thunderstorm.app.android.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun ThunderstormTheme(content: @Composable () -> Unit) {
    val colors = when (isSystemInDarkTheme()) {
        true -> darkColorPalette()
        false -> lightColorPalette()
    }
    MaterialTheme(
        colors = colors,
        typography = typography(),
        content = content
    )
}