import SwiftUI
import UIKit
import Lottie

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
                HStack {
                    VStack {
                        Text(
                            String(Int(viewModel.currentWeatherData!.current.tempFarenheit)) + "°"
                        )
                        .font(.custom(TexGyreHerosBold, size: 80))
                        Text(viewModel.currentWeatherData!.current.condition.text)
                            .font(.custom(ManropeRegular, size: 19))
                    }
                    Spacer()
                    Image(viewModel.currentWeatherIcon!)
                        .resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(width: 120, height: 120)
                }
                .padding(.top, 40)
                .padding(.leading, 20)
                .padding(.trailing, 20)
            } else {
                
            }
            FullscreenPlaceholder()
        }
        .navigationBarBackButtonHidden(true)
        .navigationTitle("")
    }
}
