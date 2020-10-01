import SwiftUI
import common

struct ContentView: View {
    let homeViewModel = ServiceLocator().getHomeViewModel()
    
    var body: some View {
        Text(homeViewModel.title.localized())
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
