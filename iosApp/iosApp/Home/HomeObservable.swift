//
//  HomeObservable.swift
//  iosApp
//
//  Created by Nagy Robert on 29/10/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import common

class HomeObservable: ObservableObject{
    
    let homeViewModel = ServiceLocator().getHomeViewModel()
    @Published private(set) var categoryTabs: Array<CategoryTabUiModel> = [CategoryTabUiModel]()
    
    init() {
        ExtensionsKt.onEachHelper(homeViewModel.categoryTabs) { items in
            if let categories = items as? Array<CategoryTabUiModel>{
                self.categoryTabs = categories
            }
        }
    }
}
