import SwiftUI
import shared

struct QuickDetailView: View {
    var weatherData: WeatherDataResult
    var body: some View {
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
                        time: weatherData.forecast.forecastDay[0].astronomy.sunrise
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
                        time: weatherData.forecast.forecastDay[0].astronomy.sunset
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
        VStack {
            HStack {
                QuickDetailCard(
                    mainText: "\(weatherData.current.humidity)%",
                    subtitle: "weather_detail_humidity_text"
                )
                Spacer()
                QuickDetailCard(
                    mainText: "\(Int(weatherData.current.uv))",
                    subtitle: "weather_detail_uv_index_text"
                )
            }
            .frame(width: .infinity)
            .padding(.top, 15)
            .padding(.horizontal, 20)
            HStack {
                QuickDetailCard(
                    mainText:
                        "weather_air_quality_" +
                        String(Int(weatherData.current.airQuality.usEpaIndex)),
                    subtitle: "weather_detail_air_quality_text",
                    airQualityText: true
                )
                Spacer()
                QuickDetailCard(
                    mainText: "\(Int(weatherData.current.visibilityMi)) mi",
                    subtitle: "weather_detail_visibility_text"
                )
            }
            .frame(width: .infinity)
            .padding(.top, 5)
            .padding(.horizontal, 20)
        }
        
    }
}

struct QuickDetailCard: View {
    var mainText: String
    var subtitle: LocalizedStringKey
    var airQualityText: Bool = false
    var body: some View {
        ZStack(alignment: .leading) {
            Color("InterfaceGray")
                .cornerRadius(10)
                .opacity(0.5)
            VStack(alignment: .leading) {
                if (airQualityText) {
                    Text(LocalizedStringKey(mainText))
                        .font(.custom(TexGyreHerosBold, size: 27))
                        .padding(.leading, 14)
                        .padding(.top, 5)
                } else {
                    Text(mainText)
                        .font(.custom(TexGyreHerosBold, size: 33))
                        .padding(.leading, 15)
                        .padding(.top, 5)
                }
                Text(subtitle)
                    .font(.custom(ManropeRegular, size: 16))
                    .padding(.leading, 15)
                    .padding(.bottom, 15)
            }
        }
        .padding(.trailing, 5)
    }
}
