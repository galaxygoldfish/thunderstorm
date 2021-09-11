package com.thunderstorm.app.database

import com.squareup.sqldelight.db.SqlDriver
import com.thunderstorm.app.ThunderstormDatabase

expect class DatabaseDriver {
    fun createDriver(): SqlDriver
}

fun createDatabase(databaseDriver: DatabaseDriver) {
    val driver = databaseDriver.createDriver()
    val database = ThunderstormDatabase(driver)
}
