package com.thunderstorm.app.android.theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.thunderstorm.app.android.R

@Composable
fun darkColorPalette() = darkColors(
    primary = colorResource(id = R.color.thunderstorm_accent_color),
    primaryVariant = colorResource(id = R.color.thunderstorm_accent_color),
    secondary = colorResource(id = R.color.thunderstorm_accent_color),
    background = Color.Black,
    surface = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
)

@Composable
fun lightColorPalette() = lightColors(
    primary = colorResource(id = R.color.thunderstorm_accent_color),
    primaryVariant = colorResource(id = R.color.thunderstorm_accent_color),
    secondary = colorResource(id = R.color.thunderstorm_accent_color),
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)