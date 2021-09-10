import SwiftUI
import Foundation
import shared

class SetupViewModel: ObservableObject {

    @Published var cityList: [SearchCityResult] = []
    @Published var allowNavigateNext: Bool = false
    
    @Published var selectionTemperature = 0
    @Published var selectionSpeed = 0
    @Published var selectionPrecip = 0
    @Published var selectionAir = 0
   
    func fetchCitiesForSearch(query: String?) {
        if (query != "" && query != nil) {
            NetworkingClient().getMatchingCitiesForSearch(
                       query: query,
                       completionHandler: { result, error in
                           if (error == nil) {
                               self.cityList.removeAll()
                               result?.forEach { item in
                                   self.cityList.append(item)
                               }
                           }
                       }
                   )
        }
    }
    
}
