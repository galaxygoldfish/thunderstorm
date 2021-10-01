import shared
import SwiftUI

struct WeatherAlertListView: View {
    
    @State private var navigateBack: Bool = false
    @EnvironmentObject private var viewModel: AlertViewModel
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack(alignment: .center) {
                NavigationLink(
                    destination: WeatherView(),
                    isActive: $navigateBack
                ) {
                    Button(
                        action: {
                            navigateBack = true
                        },
                        label: {
                            Image("BackArrow")
                                .padding(.leading, 20)
                        }
                    )
                }
                Text(LocalizedStringKey("alert_list_header_title"))
                    .font(.custom(TexGyreHerosBold, size: 22))
                    .padding(.leading, 15)
            }
            .padding(.top, 25)
            .padding(.bottom, 20)
            ScrollView {
                VStack(alignment: .leading) {
                    ForEach(viewModel.alertsAvailable, id: \.self) { alert in
                        NavigationLink(
                            destination: WeatherAlertDetail(alertInfo: alert)
                                .environmentObject(
                                    AlertViewModel(cityUrl:viewModel.currentCity)
                                )
                        ) {
                            AlertListItem(alertDetails: alert)
                        }
                        
                    }
                }
            }
        }
        .navigationTitle("")
        .navigationBarBackButtonHidden(true)
        .navigationBarHidden(true)
    }
}

struct AlertListItem: View {
    let alertDetails: AlertWeatherObject
    var body: some View {
        ZStack(alignment: .leading) {
            Color("InterfaceGray")
                .cornerRadius(10)
                .opacity(0.5)
            HStack(alignment: .center) {
                Image("WarningIcon")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 24, height: 24)
                    .padding(.leading, 20)
                VStack(alignment: .leading) {
                    Text(alertDetails.headline)
                        .font(.custom(ManropeBold, size: 18))
                        .foregroundColor(Color("AccentColor"))
                    Text(alertDetails.event)
                        .font(.custom(ManropeRegular, size: 17))
                        .foregroundColor(Color("AccentColor"))
                }
                .padding(.leading, 15)
                .padding(.trailing, 15)
            }
            .padding(.vertical, 18)
        }
        .frame(width: .infinity, alignment: .leading)
        .padding(.horizontal, 20)
    }
}
