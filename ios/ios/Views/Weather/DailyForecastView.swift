import SwiftUI
import shared

struct DailyForecastView: View {
    var weatherData: WeatherDataResult
    var body: some View {
        VStack {
            ForEach(1...weatherData.forecast.forecastDay.count, id: \.self) { item in
                DailyListItem(weatherData: weatherData.forecast.forecastDay[item - 1], isDay: 1)
            }
        }
        .padding(.top, 15)
    }
}

struct DailyListItem: View {
    let weatherData: ForecastDayWeatherObject
    let isDay: Int
    var body: some View {
        ZStack(alignment: .leading) {
            Color("InterfaceGray")
                .cornerRadius(10)
                .opacity(0.5)
            HStack(alignment: .center) {
                Image(
                    getIconForCodeAndName(
                        isDay: Int32(isDay),
                        code: weatherData.dayDetails.condition.code
                    )
                )
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 30, height: 30)
                    .padding(.leading, 15)
                Text(parseWeekday(date: weatherData.date))
                    .font(.custom(ManropeRegular, size: 18))
                    .padding(.leading, 10)
                Spacer()
                HStack(alignment: .bottom) {
                    Text(String(Int(weatherData.dayDetails.highTempFahrenheit)) + "°")
                        .font(.custom(TexGyreHerosBold, size: 27))
                        .padding(.trailing, 0)
                    Text(String(Int(weatherData.dayDetails.lowTempFahrenheit)) + "°")
                        .font(.custom(TexGyreHerosBold, size: 20))
                        .colorMultiply(Color("AccentColor").opacity(0.5))
                        .padding(.trailing, 15)
                        .padding(.leading, 0)
                        .padding(.bottom, 1)
                }
            }
            .padding(.vertical, 10)
        }
        .padding(.horizontal, 20)
        .padding(.bottom, 8)
    }
}

func parseWeekday(date: String) -> String {
    let dateFormat = DateFormatter()
    dateFormat.dateFormat = "yyyy-mm-dd"
    let parsedDate = dateFormat.date(from: date)!
    return dateFormat.weekdaySymbols[Calendar.current.component(.weekday, from: parsedDate) - 3] // subtract 3 to get correctly formatted weekday
}

func getCurrentTime() -> String {
    let dateFormatter = DateFormatter();
    dateFormatter.dateFormat = "h:mm a";
    return dateFormatter.string(from: Date())
}

