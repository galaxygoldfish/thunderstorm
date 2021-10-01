import shared
import SwiftUI

struct WeatherAlertDetail: View {
    
    var alertFields: [String]
    
    init(alertInfo: AlertWeatherObject) {
        alertFields = [
            alertInfo.headline, alertInfo.severity, alertInfo.urgency,
            alertInfo.areas, alertInfo.category, alertInfo.event,
            alertInfo.note, alertInfo.desc, alertInfo.instruction
        ]
    }
    
    @State private var navigateBack: Bool = false
    @EnvironmentObject var viewModel: AlertViewModel
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack(alignment: .center) {
                NavigationLink(
                    destination: WeatherAlertListView()
                        .environmentObject(viewModel),
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
                Text(LocalizedStringKey("alert_detail_header_title"))
                    .font(.custom(TexGyreHerosBold, size: 22))
                    .padding(.leading, 15)
            }
            .padding(.top, 25)
            .padding(.bottom, 20)
            ScrollView {
                VStack(alignment: .leading) {
                    ForEach(1...alertFields.count, id: \.self) { index in
                        VStack(alignment: .leading) {
                            Text(
                                LocalizedStringKey(viewModel.alertDetailKeys[index - 1])
                            )
                                .font(.custom(ManropeBold, size: 20))
                                
                            Text(alertFields[index - 1])
                                .font(.custom(ManropeRegular, size: 18))
                        }
                        .frame(width: .infinity, alignment: .leading)
                        .padding(.leading, 15)
                        .padding(.trailing, 20)
                        .padding(.bottom, 10)
                    }
                }
                .frame(width: .infinity)
            }
        }
        .navigationTitle("")
        .navigationBarHidden(true)
        .navigationBarBackButtonHidden(true)
    }
}
