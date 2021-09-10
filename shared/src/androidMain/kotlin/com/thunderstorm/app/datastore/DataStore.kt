package com.thunderstorm.app.datastore

import android.app.Activity
import android.content.Context.MODE_PRIVATE

val DataStoreName = "com.thunderstorm.app.preference"

actual typealias SharedContext = Activity

actual fun SharedContext.insertBoolean(key: String, value: Boolean) {
    this.getSharedPreferences(DataStoreName, MODE_PRIVATE).edit().apply {
        putBoolean(key, value)
        apply()
    }
}

actual fun SharedContext.getBoolean(key: String): Boolean {
    return this.getSharedPreferences(DataStoreName, MODE_PRIVATE).getBoolean(key, false)
}

actual fun SharedContext.insertString(key: String, value: String) {
    this.getSharedPreferences(DataStoreName, MODE_PRIVATE).edit().apply {
        putString(key, value)
        apply()
    }
}

actual fun SharedContext.getString(key: String): String{
    return this.getSharedPreferences(DataStoreName, MODE_PRIVATE).getString(key, "").toString()
}

actual fun SharedContext.insertInteger(key: String, value: Int) {
    this.getSharedPreferences(DataStoreName, MODE_PRIVATE).edit().apply {
        putInt(key, value)
        apply()
    }
}

actual fun SharedContext.getInteger(key: String): Int {
    return this.getSharedPreferences(DataStoreName, MODE_PRIVATE).getInt(key, 0)
}