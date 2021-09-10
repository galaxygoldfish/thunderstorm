import SwiftUI
import UIKit
import shared

struct CustomizationView: View {
    
    @EnvironmentObject var viewModel: SetupViewModel
    
    var body: some View {
        VStack(alignment: .leading) {
            Image("SwitchDouble")
                .resizable()
                .renderingMode(.template)
                .frame(width: 40, height: 40)
                .padding(.leading, 20)
                .padding(.top, 20)
            Text(LocalizedStringKey("customization_header_title"))
                .font(.custom(TexGyreHerosBold, size: 34))
                .padding(.leading, 20)
                .padding(.top, 3)
            ScrollView {
                CustomizationPreference(
                    title: LocalizedStringKey("customization_temp_unit_title"),
                    availableOptions: [
                        LocalizedStringKey("customization_temp_unit_f"),
                        LocalizedStringKey("customization_temp_unit_c")
                    ],
                    selectionManager: viewModel.selectionTemperature
                )
                CustomizationPreference(
                    title: LocalizedStringKey("customization_speed_unit_title"),
                    availableOptions: [
                        LocalizedStringKey("customization_speed_unit_mph"),
                        LocalizedStringKey("customization_speed_unit_kph")
                    ],
                    selectionManager: viewModel.selectionSpeed
                )
                CustomizationPreference(
                    title: LocalizedStringKey("customization_precip_unit_title"),
                    availableOptions: [
                        LocalizedStringKey("customization_precip_unit_in"),
                        LocalizedStringKey("customization_precip_unit_mm")
                    ],
                    selectionManager: viewModel.selectionPrecip
                )
                CustomizationPreference(
                    title: LocalizedStringKey("customization_air_unit_title"),
                    availableOptions: [
                        LocalizedStringKey("customization_air_unit_in"),
                        LocalizedStringKey("customization_air_unit_mm")
                    ],
                    selectionManager: viewModel.selectionAir
                )
            }
            .frame(width: .infinity, height: .infinity)
        }
        .navigationBarBackButtonHidden(true)
        .navigationBarHidden(true)
    }
}

struct CustomizationPreference: View {
    
    var title: LocalizedStringKey
    var availableOptions: Array<LocalizedStringKey>
    
    @State var selectionManager: Int
    
    @State var showOptions: Bool = false
    
    var body: some View {
        ZStack {
            Color("InterfaceGrayAlt")
                    .cornerRadius(10)
            HStack {
                Text(title)
                    .font(.custom(ManropeBold, size: 16))
                Spacer()
                Text(availableOptions[selectionManager])
                    .colorMultiply(Color("DefaultTextColor"))
                    .opacity(0.6)
                    .font(.custom(ManropeRegular, size: 15))
            }
            .padding(.horizontal, 15)
        }
        .frame(width: .infinity, height: 50)
        .padding(.horizontal, 20)
        .padding(.bottom, 5)
        .actionSheet(isPresented: $showOptions) {
            ActionSheet(
                title: Text(title),
                buttons: [
                    .default(
                        Text(availableOptions[0]),
                        action: {
                            selectionManager = 0
                        }
                    ),
                    .default(
                        Text(availableOptions[1]),
                        action: {
                            selectionManager = 1
                        }
                    ),
                    .cancel()
                ]
            )
        }
        .onTapGesture {
            showOptions = true
        }
    }
}
