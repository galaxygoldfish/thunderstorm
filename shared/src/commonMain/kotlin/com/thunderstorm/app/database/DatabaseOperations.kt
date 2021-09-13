package com.thunderstorm.app.database

import com.squareup.sqldelight.db.SqlDriver
import com.thunderstorm.app.ThunderstormDatabase

expect class DatabaseDriver {
    fun createDriver(): SqlDriver
}

/**
 * Workaround to create database instance on iOS
 * where creating one normally results in an error,
 * because "there are no available constructors for
 * class ThunderstormDatabase"
 */
fun createDatabase(databaseDriver: SqlDriver) : ThunderstormDatabase {
    return ThunderstormDatabase(databaseDriver)
}
