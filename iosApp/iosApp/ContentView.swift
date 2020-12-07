import SwiftUI
import common

struct ContentView: View {
    var body: some View {
        NavigationView{
            TabView {
                HomeView()
                    .tabItem {
                        Image("ic_home_outline")
                        Text(MR.strings().home.localize())
                    }
                NavigationView{
                    FavoritesView()
                }
                .tabItem{
                    Image("ic_favorite_outline")
                    Text(MR.strings().favourites.localize())
                }
                SettingsView()
                    .tabItem {
                        Image("ic_settings_outline")
                        Text(MR.strings().settings.localize())
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
