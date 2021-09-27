import SwiftUI
import shared
import Foundation

struct CurrentWeatherView: View {
    
    var weatherData: WeatherDataResult
    var weatherIcon: String
    
    var temperatureMain: Int
    var temperatureFeelsLike: Int
    
    init(weatherData: WeatherDataResult, weatherIcon: String) {
        self.weatherData = weatherData
        self.weatherIcon = weatherIcon
        let dataStore = DataStore(context: NSObject())
        if (dataStore.getInteger(key: "PREF_TEMP_UNITS") == 0) {
            temperatureMain = Int(weatherData.current.tempFarenheit)
            temperatureFeelsLike = Int(weatherData.current.tempFarenheit)
        } else {
            temperatureMain = Int(weatherData.current.tempCelsius)
            temperatureFeelsLike = Int(weatherData.current.feelsLikeCelsius)
        }
    }
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                Text("\(temperatureMain)°")
                    .font(.custom(TexGyreHerosBold, size: 80))
                Spacer()
                Image(weatherIcon)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 120, height: 120)
            }
            .padding(.top, 35)
            .padding(.leading, 20)
            .padding(.trailing, 20)
            Text(
                weatherData.current.condition.text
            )
                .font(.custom(ManropeRegular, size: 19))
                .padding(.leading, 20)
            Text(
                LocalizedStringKey(
                    "weather_feels_like_template_\(String(temperatureFeelsLike))"
                )
            )
                .font(.custom(ManropeRegular, size: 15))
                .colorMultiply(.gray)
                .padding(.leading, 20)
        }
    }
}
