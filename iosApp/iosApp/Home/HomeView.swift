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
    
    @ObservedObject var homeObservable = HomeObservable()
    @State private var selectedTab: Int = 0
    
    var body: some View {
        VStack {
            let tabs = homeObservable.categoryTabs.map { categoryTabs in
                categoryTabs.name
            }
            if(tabs.count >= 2){
                ScrollView(.horizontal){
                    SlidingTabView(selection: self.$selectedTab, tabs: tabs)
                }
            }else{
                ProgressView().frame(alignment: .center)
            }
            Text("\(selectedTab)")
            Spacer()
        }
    }
}

//struct HomeView_Previews: PreviewProvider {
//    static var previews: some View {
//        HomeView()
//    }
//}
