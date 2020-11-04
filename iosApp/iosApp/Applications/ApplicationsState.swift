//
//  ApplicationsState.swift
//  iosApp
//
//  Created by Nagy Robert on 04/11/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import common

class ApplicationsState: ObservableObject{
    
    let categoryId: Int64
    let viewModel: ApplicationsViewModel
    
    @Published private(set) var applications = [ApplicationUiModel.App]()
    @Published private(set) var isLoading = true
    
    init(categoryId: Int64) {
        self.categoryId = categoryId
        viewModel = ServiceLocator().getApplicationsViewModel(categoryId: categoryId)
        
        // TODO handle other types like loading
        ExtensionsKt.onEachHelper(viewModel.applications) { uiItems in
            if let items = uiItems as? Array<ApplicationUiModel>{
                self.applications = items.filter { $0 is ApplicationUiModel.App}.map { $0 as! ApplicationUiModel.App}
                self.isLoading = items.contains{ $0 is ApplicationUiModel.Loading}
            }
        }
    }
}
