package com.thunderstorm.app.database

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import com.thunderstorm.app.ThunderstormDatabase

actual class DatabaseDriver(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(ThunderstormDatabase.Schema, context, "Thunderstorm.db")
    }
}