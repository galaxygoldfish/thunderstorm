import SwiftUI
import UIKit
import Lottie
import shared

struct WeatherView: View {
    
    @EnvironmentObject private var viewModel: WeatherViewModel
    
    var body: some View {
        VStack(alignment: .leading) {
            if (viewModel.currentCityName != nil) {
                Text(viewModel.currentCityName!)
                    .font(.custom(TexGyreHerosBold, size: 40))
                    .padding(.top, 20)
                    .padding(.leading, 20)
                Text(viewModel.currentCityRegion!)
                    .font(.custom(ManropeRegular, size: 14))
                    .padding(.leading, 20)
            }
            if (viewModel.currentWeatherData != nil) {
                let currentWeatherData = viewModel.currentWeatherData!
                ScrollView {
                    VStack(alignment: .leading) {
                        HStack {
                            Text(
                                String(Int(currentWeatherData.current.tempFarenheit)) + "°"
                            )
                            .font(.custom(TexGyreHerosBold, size: 80))
                            Spacer()
                            Image(viewModel.currentWeatherIcon!)
                                .resizable()
                                .aspectRatio(contentMode: .fit)
                                .frame(width: 120, height: 120)
                        }
                        .padding(.top, 35)
                        .padding(.leading, 20)
                        .padding(.trailing, 20)
                        Text(viewModel.currentWeatherData!.current.condition.text)
                            .font(.custom(ManropeRegular, size: 19))
                            .padding(.leading, 20)
                        Text(
                            LocalizedStringKey(
                                "weather_feels_like_template_\(String(Int(currentWeatherData.current.feelsLikeFarenheit)))"
                            )
                        )
                        .font(.custom(ManropeRegular, size: 15))
                        .colorMultiply(.gray)
                        .padding(.leading, 20)
                        ScrollView(.horizontal, showsIndicators: false) {
                            LazyHStack {
                                ForEach(1...currentWeatherData.forecast.forecastDay[0].hourDetails.count, id: \.self) { item in
                                    processHourlyListItem(
                                        weatherData: currentWeatherData.forecast.forecastDay[0].hourDetails[item - 1]
                                    )
                                }
                                ZStack(alignment: .top) {
                                    Color("InterfaceGrayAlt")
                                        .cornerRadius(10)
                                        .opacity(0.5)
                                    VStack {
                                        let intermediateWeather = currentWeatherData.forecast.forecastDay[1].hourDetails[0].localTime
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
                                ForEach(1...currentWeatherData.forecast.forecastDay[1].hourDetails.count, id: \.self) { item in
                                    HourlyListItem(
                                        weatherData: currentWeatherData.forecast.forecastDay[0].hourDetails[item - 1]
                                    )
                                }
                            }
                        }
                        .padding(.leading, 20)
                        .padding(.trailing, 20)
                        .padding(.top, 25)
                        ZStack(alignment: .leading) {
                            Color("InterfaceGray")
                                .cornerRadius(10)
                                .opacity(0.5)
                            HStack(alignment: .center) {
                                Image("SunIcon")
                                    .padding(.vertical, 20)
                                    .padding(.leading, 20)
                                Text(
                                    format24HourTo12Hour(
                                        time: currentWeatherData.forecast.forecastDay[0].astronomy.sunrise
                                    )
                                )
                                    .padding(.leading, 15)
                                    .font(.custom(ManropeSemiBold, size: 17))
                                Spacer()
                                VStack {
                                    Color("AccentColor")
                                        .frame(width: 1, height: 40, alignment: .center)
                                        .opacity(0.3)
                                }
                                Spacer()
                                Text(
                                    format24HourTo12Hour(
                                        time: currentWeatherData.forecast.forecastDay[0].astronomy.sunset
                                    )
                                )
                                    .padding(.trailing, 15)
                                    .font(.custom(ManropeSemiBold, size: 17))
                                Image("MoonIcon")
                                    .padding(.vertical, 20)
                                    .padding(.trailing, 20)
                                
                            }
                        }
                        .padding(.horizontal, 20)
                        .padding(.top, 15)
                        HStack {
                            ZStack(alignment: .leading) {
                                Color("InterfaceGray")
                                    .cornerRadius(10)
                                    .opacity(0.5)
                                VStack {
                                    Text("\(currentWeatherData.current.humidity)%")
                                        .font(.custom(TexGyreHerosBold, size: 33))
                                        .padding(.leading, 15)
                                        .padding(.top, 5)
                                    Text(LocalizedStringKey("weather_detail_humidity_text"))
                                        .font(.custom(ManropeRegular, size: 16))
                                        .padding(.leading, 15)
                                        .padding(.bottom, 15)
                                }
                            }
                            .padding(.trailing, 5)
                            Spacer()
                            ZStack(alignment: .leading) {
                                Color("InterfaceGray")
                                    .cornerRadius(10)
                                    .opacity(0.5)
                                VStack(alignment: .leading) {
                                    Text("\(Int(currentWeatherData.current.uv))")
                                        .font(.custom(TexGyreHerosBold, size: 33))
                                        .padding(.leading, 15)
                                        .padding(.top, 5)
                                    Text(LocalizedStringKey("weather_detail_uv_index_text"))
                                        .font(.custom(ManropeRegular, size: 16))
                                        .padding(.leading, 15)
                                        .padding(.bottom, 15)
                                }
                            }
                            .padding(.leading, 5)
                        }
                        .frame(width: .infinity)
                        .padding(.top, 15)
                        .padding(.horizontal, 20)
                        VStack {
                            ForEach(1...currentWeatherData.forecast.forecastDay.count, id: \.self) { item in
                                DailyListItem(weatherData: currentWeatherData.forecast.forecastDay[item - 1], isDay: 1)
                            }
                        }
                        .padding(.top, 15)
                    }
                }
            } else {
                FullscreenPlaceholder()
            }
        }
        .navigationBarBackButtonHidden(true)
        .navigationTitle("")
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
    let parsedDate = dateFormat.date(from: date)
    dateFormat.dateFormat = "EEEE"
    return dateFormat.string(from: parsedDate!)
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
    init(weatherData: HourWeatherObject) {
        weather = weatherData
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "HH:mm"
        let formattedDate = dateFormatter.date(from: String(weatherData.localTime.split(separator: " ")[1]))
        dateFormatter.dateFormat = "h a"
        formattedTime  = dateFormatter.string(from: formattedDate!)
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
                Text(String(Int(weather.temperatureFahrenheit)) + "°")
                    .font(.custom(ManropeSemiBold, size: 16))
                    .padding(.top, 10)
                    .padding(.bottom, 10)
            }
        }
        .padding(.trailing, 10)
    }
}
