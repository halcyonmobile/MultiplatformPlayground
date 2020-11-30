import SwiftUI
import common

struct ContentView: View {
    var body: some View {
        NavigationView{
            TabView {
                HomeView()
                    .tabItem {
                        Image("ic_home_outline")
                        Text(LocalizationsKt.home.localized())
                    }
                NavigationView{
                    FavoritesView()
                }
                .tabItem{
                    Image("ic_favorite_outline")
                    Text(LocalizationsKt.favourites.localized())
                }
                SettingsView()
                    .tabItem {
                        Image("ic_settings_outline")
                        Text(LocalizationsKt.settings.localized())
                    }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
