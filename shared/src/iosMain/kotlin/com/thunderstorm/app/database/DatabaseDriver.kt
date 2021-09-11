package com.thunderstorm.app.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver
import com.thunderstorm.app.ThunderstormDatabase

actual class DatabaseDriver {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(ThunderstormDatabase.Schema, "Thunderstorm.db")
    }
}