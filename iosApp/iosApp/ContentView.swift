import SwiftUI
import common

struct ContentView: View {
    var body: some View {
        TabView {
            HomeView()
            .tabItem {
                Image("ic_home_outline")
                Text("Home")
            }
            FavoritesView()
            .tabItem{
                Image("ic_favorite_outline")
                Text("Favorites")
            }
            SettingsView()
            .tabItem {
                Image("ic_settings_outline")
                Text("Settings")
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
