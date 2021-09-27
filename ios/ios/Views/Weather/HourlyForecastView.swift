import SwiftUI
import shared

struct HourlyForecastView: View {
    var weatherData: WeatherDataResult
    var body: some View {
        ScrollView(.horizontal, showsIndicators: false) {
            LazyHStack {
                ForEach(1...weatherData.forecast.forecastDay[0].hourDetails.count, id: \.self) { item in
                    processHourlyListItem(
                        weatherData: weatherData.forecast.forecastDay[0].hourDetails[item - 1]
                    )
                }
                ZStack(alignment: .top) {
                    Color("InterfaceGrayAlt")
                        .cornerRadius(10)
                        .opacity(0.5)
                    VStack {
                        let intermediateWeather = weatherData.forecast.forecastDay[1].hourDetails[0].localTime
                        Text("\(intermediateWeather.split(separator: "-")[1]) / " +
                             "\(intermediateWeather.split(separator: "-")[2].split(separator: " ")[0])"
                        )
                            .font(.custom(ManropeSemiBold, size: 15))
                            .padding(.top, 16)
                            .padding(.horizontal, 10)
                        Image("DoubleArrowNext")
                            .resizable()
                            .aspectRatio(contentMode: .fit)
                            .frame(width: 30, height: 30)
                            .padding(.bottom, 30)
                            .padding(.top, 25)
                    }
                }
                .padding(.trailing, 10)
                ForEach(0...weatherData.forecast.forecastDay[1].hourDetails.count, id: \.self) { item in
                    HourlyListItem(
                        weatherData: weatherData.forecast.forecastDay[0].hourDetails[item]
                    )
                }
            }
        }
        .padding(.leading, 20)
        .padding(.trailing, 20)
        .padding(.top, 25)
    }
}

func processHourlyListItem(weatherData: HourWeatherObject) -> HourlyListItem? {
    var viewOrNil: HourlyListItem? = nil
    let dateFormatter = DateFormatter()
    dateFormatter.dateFormat = "HH:mm"
    let formattedDate = dateFormatter.date(from: String(weatherData.localTime.split(separator: " ")[1]))
    dateFormatter.dateFormat = "h a"
    let currentDateFull = dateFormatter.string(from: Date())
    let targetDateFull = dateFormatter.string(from: formattedDate!)
    let targetAMPM = targetDateFull.split(separator: " ")[1]
    let currentAMPM = currentDateFull.split(separator: " ")[1]
    if (
        currentAMPM == targetAMPM &&
        currentDateFull.split(separator: " ")[0] <= targetDateFull.split(separator: " ")[0]
    ) {
        if (targetDateFull != "12 \(currentAMPM)") {
            viewOrNil = HourlyListItem(weatherData: weatherData)
        }
    }
    return viewOrNil
}

struct HourlyListItem: View {
    var weather: HourWeatherObject
    var formattedTime: String? = nil
    let weatherTemperature: Int
    init(weatherData: HourWeatherObject) {
        weather = weatherData
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "HH:mm"
        let formattedDate = dateFormatter.date(from: String(weatherData.localTime.split(separator: " ")[1]))
        dateFormatter.dateFormat = "h a"
        formattedTime  = dateFormatter.string(from: formattedDate!)
        let dataStore = DataStore(context: NSObject())
        if (dataStore.getInteger(key: "PREF_TEMP_UNITS") == 0) {
            weatherTemperature = Int(weather.temperatureFahrenheit)
        } else {
            weatherTemperature = Int(weather.temperatureCelsius)
        }
    }
    var body: some View {
        ZStack {
            Color("InterfaceGray")
                .cornerRadius(10)
                .opacity(0.5)
            VStack {
                Text(formattedTime!)
                    .font(.custom(ManropeSemiBold, size: 16))
                    .padding(.top, 10)
                Image(
                    getIconForCodeAndName(
                        isDay: weather.isDay,
                        code: weather.condition.code
                    )
                )
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 40, height: 40)
                .padding(.leading, 12)
                .padding(.trailing, 12)
                .padding(.top, 10)
                Text(String(weatherTemperature) + "°")
                    .font(.custom(ManropeSemiBold, size: 16))
                    .padding(.top, 10)
                    .padding(.bottom, 10)
            }
        }
        .padding(.trailing, 10)
    }
}
