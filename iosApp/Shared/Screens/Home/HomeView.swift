//
//  HomeView.swift
//  iosApp
//
//  Created by Zsolt Boldizsar on 9/10/20.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI
import common
import SlidingTabView

struct HomeView: View {
    @ObservedObject var state = HomeState()
    @State private var showsUploadScreen = false
    
    var body: some View {
        NavigationView {
            ZStack(alignment: .bottomTrailing) {
                let tabs = state.tabs.map { $0.name }
                if !tabs.isEmpty {
                    VStack {
                        ScrollView(.horizontal) {
                            SlidingTabView(selection: $state.selectedTab, tabs: tabs, activeAccentColor: .accentColor)
                        }
                        ApplicationsView(categoryId: state.selectedCategoryId)
                    }
                } else {
                    ProgressView()
                }
                
                FloatingActionButton(icon: "plus.circle.fill", action: { showsUploadScreen.toggle() })
                    .padding([.trailing, .bottom])
                    .sheet(isPresented: $showsUploadScreen, content: {
                        UploadApplicationView(categoryId: state.selectedCategoryId)
                    })
            }
            .navigationBarHidden(true)
        }
    }
}

//struct HomeView_Previews: PreviewProvider {
//    static var previews: some View {
//        HomeView()
//    }
//}
