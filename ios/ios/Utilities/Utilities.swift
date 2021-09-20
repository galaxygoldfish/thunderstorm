import shared
import SwiftUI

func getIconForCodeAndName(isDay: Int32, code: Int64) -> String {
    let iconName = WeatherIconCodes().getIconForWeatherCode(code: code)
    var dayNightText: String
    if (isDay == 1) {
        dayNightText = "Day"
    } else {
        dayNightText = "Night"
    }
    return "Weather\(iconName.prefix(1).capitalized + iconName.lowercased().dropFirst())\(dayNightText)"
}
