//
//  HomeView.swift
//  iosApp
//
//  Created by Zsolt Boldizsar on 9/10/20.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI
import common

struct HomeView: View {
    
    let homeViewModel = ServiceLocator().getHomeViewModel()
    
    var body: some View {
        Text(homeViewModel.title.localized())
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
