package com.thunderstorm.app.utils

class WeatherIconCodes {

    fun getIconForWeatherCode(code: Long) : String {
        return when (code) {
            1000L -> "clear"
            1003L, 1006L -> "cloudy"
            1066L -> "snowy"
            1069L, 1072L, 1249L, 1252L, 1255L -> "sleet"
            1087L, 1273L, 1276L -> "thunder"
            1114L, 1117L, 1201L, 1198L, 1204L,
            1207L, 1210L, 1213L, 1216L, 1219L,
            1222L, 1225L, 1237L, 1240L, 1258L,
            1261L, 1264L -> "snowy"
            1135L, 1147L, 1009L -> "cloudy"
            1150L, 1153L, 1168L, 1171L, 1180L,
            1183L, 1189L, 1030L, 1063L, 1192L,
            1195L, 1246L, 1243L -> "rainy"
            else -> "clear"
        }
    }

}