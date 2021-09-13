import SwiftUIPager
import SwiftUI
import UIKit
import shared

struct SetupViewPager: View {
    
    @StateObject private var setupViewModel: SetupViewModel = SetupViewModel()
    
    @State private var navigateWelcome: Bool = false
    @State private var navigateWeather: Bool = false
    
    @State var pageIndex: Int = 0
    private var pageData = [0, 1]

    func currentPage() -> Page {
        return Page.withIndex(pageIndex)
    }
    
    private func activeTint() -> Color {
        return colorScheme == .dark ? .white : .black
    }
    
    @Environment(\.colorScheme) var colorScheme
    
    var body: some View {
        GeometryReader { _ in
            VStack {
                Pager(
                    page: currentPage(),
                    data: pageData,
                    id: \.self,
                    content: { index in
                        if (index == 0) {
                            AddCityView().environmentObject(setupViewModel)
                        } else {
                            CustomizationView().environmentObject(setupViewModel)
                        }
                    }
                )
                .allowsDragging(setupViewModel.allowNavigateNext)
                .animation(.easeIn)
                HStack {
                    NavigationLink(
                        destination: WelcomeView(),
                        isActive: $navigateWelcome
                    ) {
                        Button(
                            action: {
                                if (pageIndex == 0) {
                                    navigateWelcome = true
                                } else {
                                    pageIndex = 0
                                }
                            }
                        ) {
                            Image("LeftCircle")
                                .padding(.leading, 20)
                                .padding(.bottom, 20)
                                .padding(.top, 10)
                        }
                    }
                    Spacer()
                    NavigationLink(
                        destination: WeatherView(),
                        isActive: $navigateWeather
                    ) {
                        Button(
                            action: {
                                if (setupViewModel.allowNavigateNext) {
                                    if (pageIndex == 0) {
                                       pageIndex = 1
                                    } else {
                                        let dataStore = DataStore(context: NSObject())
                                        let city = setupViewModel.selectedCity!
                                        let databaseAccess: ThunderstormDatabase = DatabaseOperationsKt.createDatabase(
                                            databaseDriver: DatabaseDriver().createDriver()
                                        )
                                        databaseAccess.cityStoreQueries.insertNewCity(
                                            cityName: city.name,
                                            stateName: city.region,
                                            countryName: city.country,
                                            serviceUrl: city.url
                                        )
                                        dataStore.putInteger(key: "PREF_TEMP_UNITS", value: Int32(setupViewModel.selectionTemperature))
                                        dataStore.putInteger(key: "PREF_SPEED_UNITS", value: Int32(setupViewModel.selectionSpeed))
                                        dataStore.putInteger(key: "PREF_PRECIP_UNITS", value: Int32(setupViewModel.selectionPrecip))
                                        dataStore.putInteger(key: "PREF_AIR_UNITS", value: Int32(setupViewModel.selectionAir))
                                        dataStore.putBoolean(key: "INDICATION_ONBOARDING_DONE", value: true)
                                        navigateWeather = true
                                    }
                                }
                            }
                        ) {
                            Image("RightCircle")
                                .renderingMode(.template)
                                .colorMultiply(setupViewModel.allowNavigateNext == true ? activeTint() : Color("InterfaceGrayAlt"))
                                .padding(.trailing, 20)
                                .padding(.bottom, 20)
                                .padding(.top, 10)
                        }
                    }
                    .foregroundColor(colorScheme == .dark ? .white : Color("InterfaceGrayAlt"))
                }
                .ignoresSafeArea(.keyboard)
            }
            .navigationTitle("")
            .navigationBarBackButtonHidden(true)
            .navigationBarHidden(true)
        }
    }
}
