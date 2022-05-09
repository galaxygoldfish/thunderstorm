package com.thunderstorm.app.android

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.glance.GlanceId
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.thunderstorm.app.android.widget.ThunderstormWidgetReceiver
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

fun <T : ViewModel> Context.getViewModel(type: Class<T>) : T
        = ViewModelProvider(this as ComponentActivity).get(type)

fun Context.updateAllWidgets() {
    val comp = ComponentName(this, ThunderstormWidgetReceiver::class.java)
    val ids = AppWidgetManager.getInstance(this).getAppWidgetIds(comp)
    val intent = Intent(this, ThunderstormWidgetReceiver::class.java).apply {
        action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
    }
    sendBroadcast(intent)
}

fun GlanceId.identifierValue() : String
    = this.toString().split("=")[1].replace(")", "")