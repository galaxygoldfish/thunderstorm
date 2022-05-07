package com.thunderstorm.app.android.widget

import android.graphics.Color
import android.util.Log
import android.util.Log.d
import android.widget.RemoteViews
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.glance.*
import androidx.glance.appwidget.AndroidRemoteViews
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import com.thunderstorm.app.android.R
import com.thunderstorm.app.android.utils.identifierValue
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.database.datastore.SharedContext


class ThunderstormWidget : GlanceAppWidget() {

    lateinit var currentWidgetID: String

    @Composable
    override fun Content() {
        currentWidgetID = LocalGlanceId.current.identifierValue()
        val dataStore = DataStore(LocalContext.current.applicationContext as SharedContext)
        val widgetBackground: Int
        var widgetReverseColor: Int
        Log.e("THUNDERSTORM", "RENDERING WIDGET ID $currentWidgetID")
        if (dataStore.getBoolean("WIDGET_${currentWidgetID}_DARK_THEME")) {
            widgetBackground = R.drawable.widget_background_dark
            widgetReverseColor = Color.WHITE
        } else {
            widgetBackground = R.drawable.widget_background_light
            widgetReverseColor = Color.BLACK
        }
        Column(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(
                    ImageProvider(widgetBackground)
                )
        ) {
            // POV you set everything up for glance compose and then
            // come to find out you can't use custom fonts
            AndroidRemoteViews(
                remoteViews = RemoteViews(
                    LocalContext.current.packageName,
                    R.layout.layout_widget_small
                ).apply {
                    val cityTitle = dataStore.getString("WIDGET_${currentWidgetID}_CITY_TITLE")
                    setTextViewText(
                        R.id.widgetSmallCityText,
                        if (cityTitle.isNotEmpty()) {
                            cityTitle.toUpperCase(Locale.current)
                            .substring(0, 3)
                        } else {
                            LocalContext.current.getString(R.string.widget_loading_long)
                        }
                    )
                    setTextViewText(
                        R.id.widgetSmallTempText,
                        dataStore.getString("WIDGET_${currentWidgetID}_TEMP_AVERAGE")
                    )
                    setTextViewText(
                        R.id.widgetSmallConditionText,
                        dataStore.getString("WIDGET_${currentWidgetID}_DESCRIPTION")
                    )
                    setTextColor(R.id.widgetSmallCityText, widgetReverseColor)
                    setTextColor(R.id.widgetSmallTempText, widgetReverseColor)
                    setTextColor(R.id.widgetSmallConditionText, widgetReverseColor)
                    setImageViewResource(
                        R.id.widgetSmallWeatherImage,
                        dataStore.getInteger("WIDGET_${currentWidgetID}_ICON_RESOURCE")
                    )
                }
            )
        }
    }

}