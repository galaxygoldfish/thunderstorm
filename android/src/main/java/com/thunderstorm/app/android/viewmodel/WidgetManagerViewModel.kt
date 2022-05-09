package com.thunderstorm.app.android.viewmodel

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.lifecycle.ViewModel
import com.thunderstorm.app.android.identifierValue
import com.thunderstorm.app.android.widget.ThunderstormWidget
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WidgetManagerViewModel : ViewModel() {

    val currentWidgetsList = mutableStateListOf<Int>()
    var showingCityDialog by mutableStateOf(false)
    var currentWidgetID by mutableStateOf<Int?>(null)

    fun fetchCurrentWidgets(context: Context) {
        currentWidgetsList.clear()
        CoroutineScope(Dispatchers.Default).launch {
            GlanceAppWidgetManager(context)
                .getGlanceIds(ThunderstormWidget::class.java)
                .forEach {
                    currentWidgetsList.add(it.identifierValue().toInt())
                }
        }
    }

}