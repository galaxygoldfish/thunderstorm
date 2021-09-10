package com.thunderstorm.app.datastore

expect class SharedContext

expect fun SharedContext.insertBoolean(key: String, value: Boolean)
expect fun SharedContext.getBoolean(key: String): Boolean

expect fun SharedContext.insertString(key: String, value: String)
expect fun SharedContext.getString(key: String): String

expect fun SharedContext.insertInteger(key: String, value: Int)
expect fun SharedContext.getInteger(key: String): Int
