import SwiftUI

struct WelcomeView: View {
    
    @State private var navigateSetup: Bool = false
    
    var body: some View {
        NavigationView {
            VStack {
                Image("WeatherThunderstormCloud")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .padding(.leading, 50)
                    .padding(.trailing, 50)
                    .padding(.top, 0)
                Text(LocalizedStringKey("welcome_page_header"))
                    .font(.custom(TexGyreHerosBold, size: 26))
                Text(LocalizedStringKey("welcome_page_subheader"))
                    .font(.custom(ManropeRegular, size: 16))
                Spacer()
                Spacer()
                NavigationLink(
                    destination: SetupViewPager(),
                    isActive: $navigateSetup
                ) {
                    Button(
                        action: {
                            navigateSetup = true
                        }
                    ) {
                        HStack {
                            Text(LocalizedStringKey("welcome_page_button_start"))
                                .font(.custom(ManropeExtraBold, size: 14))
                                .padding(.top, 12)
                                .padding(.bottom, 12)
                                .padding(.leading, 20)
                                .foregroundColor(Color("AccentColorReverse"))
                            Image("DoubleArrowNext")
                                .resizable()
                                .renderingMode(.template)
                                .colorMultiply(Color("AccentColorReverse"))
                                .foregroundColor(Color("AccentColorReverse"))
                                .frame(width: 23, height: 23)
                                .padding(.trailing, 20)
                        }
                        .background(Color("AccentColor"))
                        .cornerRadius(100)
                    }
                }
                .navigationBarBackButtonHidden(true)
                Spacer()
                Spacer()
                Spacer()
            }
        }
        .navigationBarTitle("")
        .navigationBarHidden(true)
        .navigationBarBackButtonHidden(true)
        .padding(.top, 0)
    }
}
