import shared
import SwiftUI

struct WeatherAlertListView: View {
    @State private var navigateBack: Bool = false
    var body: some View {
        VStack {
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
                                .padding(.leading, 15)
                        }
                    )
                }
                Text(LocalizedStringKey("alert_list_header_title"))
                    .font(.custom(TexGyreHerosBold, size: 22))
                    .padding(.leading, 10)
            }
            .padding(.vertical, 10)
            Spacer()
        }
    }
}
