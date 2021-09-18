package com.thunderstorm.app.android.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.thunderstorm.app.android.R

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
    h1 = TextStyle(
        fontFamily = TexGyreHeros,
        fontWeight = FontWeight.Bold,
        fontSize = 80.sp,
        color = colorResource(id = R.color.text_color)
    ),
    h2 = TextStyle(
        fontFamily = TexGyreHeros,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        color = colorResource(id = R.color.text_color)
    ),
    h3 = TextStyle(
        fontFamily = TexGyreHeros,
        fontWeight = FontWeight.Bold,
        fontSize = 35.sp,
        color = colorResource(id = R.color.text_color)
    ),
    h4 = TextStyle(
        fontFamily = TexGyreHeros,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp,
        color = colorResource(id = R.color.text_color)
    ),
    h6 = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
        color = colorResource(id = R.color.text_color)
    ),
    body1 = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = colorResource(id = R.color.text_color)
    ),
    body2 = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        color = colorResource(id = R.color.text_color)
    ),
    subtitle1 = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 19.sp,
        color = colorResource(id = R.color.text_color)
    ),
    button = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        color = colorResource(id = R.color.text_color)
    ),
    caption = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        color = colorResource(id = R.color.text_color)
    ),
    overline = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        color = colorResource(id = R.color.text_color)
    )
)