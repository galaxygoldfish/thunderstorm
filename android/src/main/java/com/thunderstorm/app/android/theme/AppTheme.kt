package com.thunderstorm.app.android.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.thunderstorm.app.android.theme.theme.Shapes

@Composable
fun ThunderstormTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = when (darkTheme) {
        true -> darkColorPalette()
        false -> lightColorPalette()
    }
    MaterialTheme(
        colors = colors,
        typography = typography(),
        shapes = Shapes,
        content = content
    )
}