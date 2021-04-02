//
//  SidebarViewModel.swift
//  App Portfolio (iOS)
//
//  Created by Botond Magyarosi on 12.01.2021.
//  Copyright © 2021 Halcyon Mobile. All rights reserved.
//

import Foundation
import SwiftUI
import common

class SidebarViewModel: ObservableObject {
    let homeViewModel = HomeViewModel()
    
    @Published private(set) var categories = [CategoryTabUiModel]()
    
    init() {
        homeViewModel.observeCategories { items in
            self.categories = items
        }
    }
    
    deinit {
        homeViewModel.dispose()
    }
}
