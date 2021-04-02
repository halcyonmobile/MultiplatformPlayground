//
//  HomeView.swift
//  iosApp
//
//  Created by Zsolt Boldizsar on 9/10/20.
//  Copyright © 2020 Halcyon Mobile. All rights reserved.
//

#if os(iOS)
import SwiftUI
import common
import SlidingTabView

struct HomeView: View {
    @ObservedObject var state = HomeState()
    @State private var showsUploadScreen = false
    
    var body: some View {
        NavigationView {
            VStack {
                let tabs = state.tabs.map { $0.name }
                if !tabs.isEmpty {
                    ScrollView(.horizontal) {
                        SlidingTabView(selection: $state.selectedTab, tabs: tabs, activeAccentColor: .accentColor)
                    }
                    ApplicationsView(categoryId: state.selectedCategoryId)
                } else {
                    ProgressView()
                }
            }
            .navigationTitle(MR.strings().app_name.localize())
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
#endif
