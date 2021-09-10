import SwiftUI
import UIKit
import shared

struct AddCityView: View {
    
    @EnvironmentObject var viewModel: SetupViewModel
    
    @State private var searchFieldText: String = ""
    @State var selectedCityItem: SearchCityResult? = nil
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                Image("MapsLocationPlace")
                    .resizable()
                    .renderingMode(.template)
                    .frame(width: 40, height: 40)
                    .padding(.leading, 20)
                    .padding(.top, 20)
                Text(LocalizedStringKey("add_city_header_title"))
                    .font(.custom(TexGyreHerosBold, size: 34))
                    .padding(.leading, 20)
                    .padding(.top, 3)
                ZStack(alignment: .leading) {
                    Color("InterfaceGray")
                        .cornerRadius(10)
                    HStack {
                        Image("SearchMagnifier")
                            .resizable()
                            .frame(width: 25, height: 25, alignment: .center)
                            .padding(.leading, 15)
                        TextField(
                            LocalizedStringKey("add_city_search_hint"),
                            text: $searchFieldText
                        )
                        .onChange(of: searchFieldText) { text in
                            viewModel.fetchCitiesForSearch(query: text)
                        }
                        .font(.custom(ManropeRegular, size: 16))
                        .padding(.trailing, 15)
                        .padding(.leading, 5)
                    }
                }
                .frame(width: .infinity, height: 50)
                .padding(.horizontal, 20)
                .padding(.top, 5)
                .cornerRadius(10)
                if (viewModel.cityList.count == 0) {
                    VStack(alignment: .center) {
                        Spacer()
                        Image("NothingHerePerson")
                            .resizable()
                            .aspectRatio(contentMode: .fill)
                            .frame(width: .infinity, height: .infinity, alignment: .center)
                            .padding(.horizontal, 70)
                        Text(LocalizedStringKey("add_city_nothing_here_hint"))
                            .font(.custom(ManropeRegular, size: 14))
                            .frame(width: .infinity, height: .infinity, alignment: .center)
                        Spacer()
                    }
                    .frame(width: .infinity, height: .infinity)
                } else {
                    ScrollView {
                        LazyVStack {
                            ForEach(viewModel.cityList, id: \.self) { result in
                                SearchResultListItem(
                                    searchResult: result,
                                    selectionTracker: $selectedCityItem
                                )
                            }
                        }
                    }
                    .frame(width: .infinity, height: .infinity)
                    .padding(.top, 5)
                }
            }
            .navigationBarBackButtonHidden(true)
            .navigationBarHidden(true)
        }
    }
}

struct SearchResultListItem: View {
    var searchResult: SearchCityResult
    @EnvironmentObject var viewModel: SetupViewModel
    @Binding var selectionTracker: SearchCityResult?
    var body: some View {
        Button( action: {
            selectionTracker = searchResult
            viewModel.allowNavigateNext = true
        }) {
            ZStack(alignment: .leading) {
                Color("InterfaceGrayAlt")
                    .cornerRadius(10)
                HStack {
                    Image("GpsLocation")
                        .padding(.leading, 20)
                    VStack(alignment: .leading) {
                        Text(searchResult.name.split(separator: ",")[0])
                            .font(.custom(ManropeBold, size: 17))
                            .frame(alignment: .leading)
                            .foregroundColor(Color("DefaultTextColor"))
                        Text(searchResult.region)
                            .font(.custom(ManropeRegular, size: 13))
                            .frame(alignment: .leading)
                            .foregroundColor(Color("DefaultTextColor"))
                        Text(searchResult.country)
                            .font(.custom(ManropeRegular, size: 13))
                            .frame(alignment: .leading)
                            .foregroundColor(Color("DefaultTextColor"))
                    }
                    .padding(.leading, 10)
                    Spacer()
                    if (selectionTracker != nil) {
                        if (selectionTracker == searchResult) {
                            Image("CheckCircle")
                                .renderingMode(.template)
                                .colorMultiply(Color("AffirmativeGreen"))
                                .foregroundColor(Color("AffirmativeGreen"))
                                .frame(width: 25, height: 25, alignment: .center)
                                .padding(.trailing, 20)
                        }
                    }
                }
                .padding(.vertical, 10)
            }
            .padding(.horizontal, 20)
        }
    }
}
