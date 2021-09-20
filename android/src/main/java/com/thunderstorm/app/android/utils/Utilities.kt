package com.thunderstorm.app.android.utils

import android.content.Context
import com.thunderstorm.app.utils.WeatherIconCodes

fun Context.getIconForNameAndCode(isDay: Int, code: Long) : Int {
    val iconName = WeatherIconCodes().getIconForWeatherCode(code)
    val dayNightIconCode = if (isDay == 1) "day" else "night"
    return resources.getIdentifier(
        """ic_${iconName}_${dayNightIconCode}""",
        "drawable",
        packageName
    )
}