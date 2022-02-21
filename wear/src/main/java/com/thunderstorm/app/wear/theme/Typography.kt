package com.thunderstorm.app.wear.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Typography
import com.thunderstorm.app.wear.R

val TexGyreHeros = FontFamily(
    Font(R.font.texgyreheros_bold, FontWeight.Bold)
)

val Manrope = FontFamily(
    Font(R.font.manrope_extrabold, FontWeight.ExtraBold),
    Font(R.font.manrope_bold, FontWeight.Bold),
    Font(R.font.manrope_semibold, FontWeight.SemiBold),
    Font(R.font.manrope_regular, FontWeight.Normal)
)

@Composable
fun typography() = Typography(
    defaultFontFamily = Manrope,
    display1 = TextStyle(
        fontFamily = TexGyreHeros,
        fontWeight = FontWeight.Bold,
        fontSize = 80.sp
    ),
    display2 = TextStyle(
        fontFamily = TexGyreHeros,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp
    ),
    display3 = TextStyle(
        fontFamily = TexGyreHeros,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    title3 = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    body1 = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    ),
    body2 = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        letterSpacing = 0.sp
    ),
    button = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    )
)
