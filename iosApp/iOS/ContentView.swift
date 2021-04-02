//
//  SidebarView.swift
//  App Portfolio (iOS)
//
//  Created by Botond Magyarosi on 05.01.2021.
//  Copyright © 2021 Halcyon Mobile. All rights reserved.
//

import SwiftUI
import common

struct ContentView: View {
    
    var body: some View {
        if UIDevice.current.userInterfaceIdiom == .pad {
            SidebarView()
        } else {
            TabView {
                HomeView()
                    .tabItem { Label(MR.strings().home.localize(), systemImage: "house") }
                FavoritesView()
                    .tabItem { Label(MR.strings().favourites.localize(), systemImage: "heart") }
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
