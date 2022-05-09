package com.thunderstorm.app.android.widget

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.thunderstorm.app.android.identifierValue
import com.thunderstorm.app.android.updateAllWidgets
import com.thunderstorm.app.android.view.CityAddView
import com.thunderstorm.app.database.datastore.DataStore
import com.thunderstorm.app.database.datastore.SharedContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WidgetConfigurator : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(Activity.RESULT_CANCELED)

        var currentWidgetID: String? = null

        CoroutineScope(Dispatchers.Default).launch {
            currentWidgetID = GlanceAppWidgetManager(this@WidgetConfigurator)
                .getGlanceIds(ThunderstormWidget::class.java)
                .last()
                .identifierValue()
        }

        setContent {
            CityAddView(
                widgetVersion = true,
                configOnBackPress = {
                    onBackPressed()
                },
                configOnFinish = { viewModel ->
                    DataStore(this.applicationContext as SharedContext).apply {
                        currentWidgetID?.let { ID ->
                            putString(
                                key = "WIDGET_${ID}_CITY_TITLE",
                                value = viewModel.currentSelectedCity!!.name.split(",")[0]
                            )
                            putString(
                                key = "WIDGET_${ID}_SERVICE_URL",
                                value = viewModel.currentSelectedCity!!.url!!
                            )
                            putBoolean(
                                key = "WIDGET_${ID}_DARK_THEME",
                                value = true
                            )
                            updateAllWidgets()
                            Intent().putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, ID).apply {
                                setResult(Activity.RESULT_OK, this)
                                finish()
                            }
                        }
                    }
                }
            )
        }
    }

}