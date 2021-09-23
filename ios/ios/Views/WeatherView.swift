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
                    }
                }
            } else {
                //
            }
        }
        .navigationBarBackButtonHidden(true)
        .navigationTitle("")
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
