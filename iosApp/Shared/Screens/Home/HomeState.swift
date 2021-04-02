//
//  HomeObservable.swift
//  iosApp
//
//  Created by Nagy Robert on 29/10/2020.
//  Copyright © 2020 Halcyon Mobile. All rights reserved.
//

import Foundation
import SwiftUI
import common

class HomeState: ObservableObject {
    
    let homeViewModel = HomeViewModel()
    
    @Published private(set) var tabs = [CategoryTabUiModel]()
    @Published private(set) var selectedCategoryId: Int64 = 0
    @Published var selectedTab = 0 {
        didSet { tabSelected(index: selectedTab) }
    }
    
    init() {
        homeViewModel.observeCategories { items in
            self.tabs = items
            
            if let categoryId = items.first(where: { $0.isSelected })?.id {
                self.selectedCategoryId = categoryId
            }
        }
    }
    
    func tabSelected(index: Int) {
        homeViewModel.onTabClicked(index: Int32(index))
    }
    
    deinit {
        homeViewModel.dispose()
    }
}

