import SwiftUI
import shared

class WeatherViewModel: ObservableObject {
    
    @Published var currentCityName: String? = nil
    @Published var currentCityRegion: String? = nil
    
    @Published var currentWeatherData: CurrentDataResult? = nil
    
    init() {
        loadCityData()
    }
    
    func loadCityData() {
        let database = DatabaseOperationsKt.createDatabase(
            databaseDriver: DatabaseDriver().createDriver()
        )
        let cityEntries = database.cityStoreQueries.getAllCities().executeAsList()
        currentCityName = String(cityEntries[0].cityName.split(separator: ",")[0])
        currentCityRegion = String(cityEntries[0].stateName)
        loadWeatherData(cityNameJson: cityEntries[0].serviceUrl)
    }
    
    func loadWeatherData(cityNameJson: String) {
        let weatherAPIClient = NetworkingClient()
        weatherAPIClient.getWeatherDataForCity(
            query: cityNameJson,
            completionHandler: { result, error in
                if (error == nil) {
                    self.currentWeatherData = result!
                }
            }
        )
    }
    
}
