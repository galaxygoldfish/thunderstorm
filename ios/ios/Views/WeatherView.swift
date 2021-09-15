import SwiftUI
import UIKit

struct WeatherView: View {
    
    @EnvironmentObject private var viewModel: WeatherViewModel
    
    var body: some View {
        VStack(alignment: .leading) {
            if (viewModel.currentCityName != nil) {
                Text(viewModel.currentCityName!)
                    .font(.custom(TexGyreHerosBold, size: 35))
                    .padding(.top, 20)
                    .padding(.leading, 20)
                Text(viewModel.currentCityRegion!)
                    .font(.custom(ManropeRegular, size: 14))
                    .padding(.leading, 20)
                if (viewModel.currentWeatherData != nil) {
                    HStack {
                        Text(String(Int(viewModel.currentWeatherData!.current.tempFarenheit)))
                            .font(.custom(TexGyreHerosBold, size: 60))
                            .padding(.leading, 20)
                    }
                }
            }
            FullscreenPlaceholder()
        }
        .navigationBarBackButtonHidden(true)
        .navigationTitle("")
    }
}
