package com.thunderstorm.app.android.widget

import android.appwidget.AppWidgetManager
import android.content.Context
import android.os.Build.ID
import android.util.Log
import androidx.glance.GlanceId
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.action.actionRunCallback
import com.thunderstorm.app.android.utils.getIconForNameAndCode
import com.thunderstorm.app.android.utils.identifierValue
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.database.datastore.SharedContext
import com.thunderstorm.app.networking.NetworkingClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class ThunderstormWidgetReceiver : GlanceAppWidgetReceiver() {

    override val glanceAppWidget: GlanceAppWidget = ThunderstormWidget()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        appWidgetIds.forEach { ID ->
            updateWidgetData(context, ID)
            CoroutineScope(Dispatchers.Default).launch {
                GlanceAppWidgetManager(context)
                    .getGlanceIds(ThunderstormWidget::class.java)
                    .forEach { glanceId ->
                        if (glanceId.identifierValue() == ID.toString()) {
                            ThunderstormWidget().update(context, glanceId)
                        }
                    }
            }
        }
    }
}

fun updateWidgetData(
    context: Context,
    ID: Int
) {
    val dataStore = DataStore(context.applicationContext as SharedContext)
    val networkClient = NetworkingClient()
    val serviceUrl = dataStore.getString("WIDGET_${ID}_SERVICE_URL")
    if (serviceUrl.isNotEmpty()) {
        CoroutineScope(Dispatchers.Default).launch {
            networkClient.getWeatherDataForCity(serviceUrl) { result ->
                val temp = if (dataStore.getBoolean("USE_IMPERIAL_UNITS")) {
                    "${result.current.tempFarenheit.roundToInt()}°"
                } else {
                    "${result.current.tempCelsius.roundToInt()}°"
                }
                dataStore.putString(
                    key = "WIDGET_${ID}_TEMP_AVERAGE",
                    value = temp
                )
                dataStore.putString(
                    key = "WIDGET_${ID}_DESCRIPTION",
                    value = result.current.condition.text
                )
                dataStore.putInteger(
                    key = "WIDGET_${ID}_ICON_RESOURCE",
                    value = context.getIconForNameAndCode(
                        isDay = result.current.isDay,
                        code = result.current.condition.code
                    )
                )
            }
        }
    }
}