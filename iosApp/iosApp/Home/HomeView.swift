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
    @State private var openApplicationDetail = false
    
    var body: some View {
        ZStack(alignment: .bottomTrailing) {
            VStack {
                let tabs = homeState.categoryTabs.map { categoryTabs in
                    categoryTabs.name
                }
                if(tabs.count >= 2){
                    ScrollView(.horizontal){
                        SlidingTabView(selection: Binding(
                                        get: { (homeState.categoryTabs.firstIndex(where: {$0.id == homeState.selectedCategoryId}) ?? 0)},
                                        set:{ selectedTabIndex in
                                            homeState.homeViewModel.onTabClicked(index: Int32(selectedTabIndex))
                                        }), tabs: tabs)
                    }
                    ApplicationsView(categoryId: homeState.selectedCategoryId)
                }else{
                    ProgressView().frame(alignment: .center)
                }
            }.navigationBarTitle("")
            .navigationBarHidden(true)
            
            NavigationLink(destination: UploadApplicationView(categoryId: homeState.selectedCategoryId), isActive: $openApplicationDetail){
                FloatingActionButton(icon: "plus.circle.fill", action: {
                    openApplicationDetail = true
                }).padding(16)
            }
        }
    }
}

//struct HomeView_Previews: PreviewProvider {
//    static var previews: some View {
//        HomeView()
//    }
//}
