import SwiftUI
import Foundation
import shared

class SetupViewModel: ObservableObject {

    @Published var cityList: [SearchCityResult] = []
    @Published var allowNavigateNext: Bool = false
    @Published var selectedCity: SearchCityResult? = nil
    
    @Published var selectionTemperature = 0
    @Published var selectionSpeed = 0
    @Published var selectionPrecip = 0
    @Published var selectionAir = 0
    
    func fetchCitiesForSearch(query: String?) {
        if (query != "" && query != nil) {
            NetworkingClient().getMatchingCitiesForSearch(
                query: query,
                onResultAvailable: { result in
                    self.cityList.removeAll()
                    if (!result.isEmpty) {
                        result.forEach { item in
                            self.cityList.append(item)
                        }
                    }
                }
            )
        }
    }
    
}
