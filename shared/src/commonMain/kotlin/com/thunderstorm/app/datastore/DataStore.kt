package com.thunderstorm.app.datastore

class DataStore(private val context: SharedContext) {

    fun putString(key: String, value: String) = context.insertString(key, value)

    fun getString(key: String): String = context.getString(key)

    fun putBoolean(key: String, value: Boolean) = context.insertBoolean(key, value)

    fun getBoolean(key: String): Boolean = context.getBoolean(key)

    fun putInteger(key: String, value: Int) = context.insertInteger(key, value)

    fun getInteger(key: String): Int = context.getInteger(key)

}