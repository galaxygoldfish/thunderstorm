import SwiftUI
import shared

class AlertViewModel: ObservableObject {
    
    @Published var alertsAvailable: [AlertWeatherObject] = []
    @Published var currentCity: String
    
    @Published var alertDetailKeys: [String] = [
        "alert_detail_headline_title", "alert_detail_severity_title",
        "alert_detail_urgency_title", "alert_detail_areas_title",
        "alert_detail_category_title","alert_detail_event_title",
        "alert_detail_note_title", "alert_detail_desc_title",
        "alert_detail_instruction_title"
    ]
    
    init(cityUrl: String) {
        currentCity = cityUrl
        loadAlertData()
    }
    
    func loadAlertData() {
        NetworkingClient().getWeatherDataForCity(
            query: currentCity,
            completionHandler: { data, error in
                let alertList = data!.alerts.alert
                if (error == nil && alertList != nil) {
                    self.alertsAvailable = alertList as! [AlertWeatherObject]
                }
            }
        )
    }
        
}
