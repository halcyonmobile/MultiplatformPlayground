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
    @Published private(set) var state: ApplicationsViewModel.State = ApplicationsViewModel.State.normal
    
    init(categoryId: Int64) {
        self.categoryId = categoryId
        viewModel = ServiceLocator().getApplicationsViewModel(categoryId: categoryId)
        
        // TODO handle other types like loading
        viewModel.observeItems { items in
            self.applications = items.filter { $0 is ApplicationUiModel.App}.map { $0 as! ApplicationUiModel.App}
            self.isLoading = items.contains{ $0 is ApplicationUiModel.Loading}
        }
        viewModel.observeState{ state in
            self.state = state
        }
        viewModel.observeEvent{ event in
            switch event {
            case ApplicationsViewModel.Event.error:
                // TODO send out error
                break
            default:
                break
            }
        }
    }
    
    deinit {
        viewModel.dispose()
    }
}
