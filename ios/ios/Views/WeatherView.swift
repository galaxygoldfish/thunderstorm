import SwiftUI
import Lottie
import shared

struct WeatherView: View {
    
    @EnvironmentObject private var viewModel: WeatherViewModel
    
    var body: some View {
        NavigationView {
            VStack(alignment: .leading) {
                if (viewModel.currentCityName != nil) {
                    HStack {
                        VStack(alignment: .leading) {
                            Text(viewModel.currentCityName!)
                                .font(.custom(TexGyreHerosBold, size: 40))
                                .padding(.top, 20)
                                .padding(.leading, 20)
                            Text(viewModel.currentCityRegion!)
                                .font(.custom(ManropeRegular, size: 14))
                                .padding(.leading, 20)
                        }
                        Spacer()
                        
                        HStack {
                            if (viewModel.weatherAlertAvailable) {
                                NavigationLink(
                                    destination: WeatherAlertListView()
                                        .environmentObject(
                                            AlertViewModel(cityUrl: viewModel.cityServiceUrl!)
                                        )
                                ) {
                                    Image("WarningIcon")
                                        .resizable()
                                        .aspectRatio(contentMode: .fit)
                                        .frame(width: 24, height: 24)
                                        .padding(.trailing, 15)
                                        .foregroundColor(Color("AccentColor"))
                                }
                            }
                            Button(
                                action: {
                                    
                                }
                            ) {
                                Image("SettingsIcon")
                                    .resizable()
                                    .aspectRatio(contentMode: .fit)
                                    .frame(width: 24, height: 24)
                                    .padding(.trailing, 15)
                            }
                        }
                        
                    }
                    .frame(width: .infinity, alignment: .leading)
                }
                if (viewModel.currentWeatherData != nil) {
                    let currentWeatherData = viewModel.currentWeatherData!
                    ScrollView(showsIndicators: false) {
                        CurrentWeatherView(
                            weatherData: currentWeatherData,
                            weatherIcon: viewModel.currentWeatherIcon!
                        )
                        HourlyForecastView(
                            weatherData: currentWeatherData
                        )
                        QuickDetailView(
                            weatherData: currentWeatherData
                        )
                        DailyForecastView(
                            weatherData: currentWeatherData
                        )
                        WeatherCreditFooter()
                    }
                } else {
                    FullscreenPlaceholder()
                }
            }
            .navigationBarBackButtonHidden(true)
            .navigationTitle("")
            .navigationBarHidden(true)
        }
        .navigationBarBackButtonHidden(true)
        .navigationTitle("")
        .navigationBarHidden(true)
    }
}

struct WeatherCreditFooter: View {
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text(LocalizedStringKey("weather_service_credit_text"))
                    .font(.custom(ManropeSemiBold, size: 16))
                Text(
                    LocalizedStringKey(
                        "weather_last_updated_template_\(getCurrentTime())"
                    )
                )
                    .font(.custom(ManropeRegular, size: 14))
                    .colorMultiply(Color("AccentColor").opacity(0.5))
            }
            .padding(.leading, 20)
            .padding(.bottom, 20)
            Spacer()
        }
        .frame(width: .infinity, alignment: .leading)
    }
}
