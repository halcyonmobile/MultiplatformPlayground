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
    
    @ObservedObject var homeState = HomeState()
    @State private var selectedTab: Int = 0
    
    var body: some View {
        VStack(alignment: .trailing) {
            let tabs = homeState.categoryTabs.map { categoryTabs in
                categoryTabs.name
            }
            if(tabs.count >= 2){
                ScrollView(.horizontal){
                    SlidingTabView(selection: self.$selectedTab, tabs: tabs)
                }
            }else{
                ProgressView().frame(alignment: .center)
            }
            Spacer()
            FloatingActionButton(icon: "plus.circle.fill", action: {})
                .padding(16)
        }
    }
}

//struct HomeView_Previews: PreviewProvider {
//    static var previews: some View {
//        HomeView()
//    }
//}
