import SwiftUI
import shared

struct CurrentWeatherView: View {
    var weatherData: WeatherDataResult
    var weatherIcon: String
    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                Text(
                    "\(Int(weatherData.current.tempFarenheit))°"
                )
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
                    "weather_feels_like_template_\(String(Int(weatherData.current.feelsLikeFarenheit)))"
                )
            )
                .font(.custom(ManropeRegular, size: 15))
                .colorMultiply(.gray)
                .padding(.leading, 20)
        }
    }
}
