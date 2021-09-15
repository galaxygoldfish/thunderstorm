import SwiftUI
import shared

@main
struct iOSApp: App {
    
    private let dataStore = DataStore(context: NSObject())
    
	var body: some Scene {
		WindowGroup {
            if (dataStore.getBoolean(key: "INDICATION_ONBOARDING_DONE")) {
                WeatherView().environmentObject(WeatherViewModel())
            } else {
                WelcomeView()
            }
		}
	}
}
