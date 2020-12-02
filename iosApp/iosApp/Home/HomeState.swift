//
//  HomeObservable.swift
//  iosApp
//
//  Created by Nagy Robert on 29/10/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import common

class HomeState: ObservableObject {
    
    let homeViewModel = HomeViewModel()
    @Published private(set) var categoryTabs = [CategoryTabUiModel]()
    @Published private(set) var selectedCategoryId: Int64 = 0
    
    init() {
        homeViewModel.observeCategories{ items in
            self.categoryTabs = items
            
            if let categoryId = items.first(where: { tab in
                tab.isSelected
            })?.id {
                self.selectedCategoryId = categoryId
            }
        }
    }
    
    deinit {
        homeViewModel.dispose()
    }
}

