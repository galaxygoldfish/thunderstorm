package com.thunderstorm.app.database.datastore

import platform.Foundation.NSUserDefaults
import platform.darwin.NSObject
import platform.darwin.StrFileName

actual typealias SharedContext = NSObject

actual fun SharedContext.insertBoolean(key: String, value: Boolean) {
    NSUserDefaults.standardUserDefaults.setBool(value, key)
}

actual fun SharedContext.getBoolean(key: String): Boolean {
    return NSUserDefaults.standardUserDefaults.boolForKey(key) ?: false
}

actual fun SharedContext.insertString(key: String, value: String) {
    NSUserDefaults.standardUserDefaults.setObject(value, key)
}

actual fun SharedContext.getString(key: String): String {
    return NSUserDefaults.standardUserDefaults.stringForKey(key) ?: ""
}

actual fun SharedContext.insertInteger(key: String, value: Int) {
    NSUserDefaults.standardUserDefaults.setInteger(value.toLong(), key)
}

actual fun SharedContext.getInteger(key: String): Int {
    return (NSUserDefaults.standardUserDefaults.integerForKey(key) ?: 0).toInt()
}