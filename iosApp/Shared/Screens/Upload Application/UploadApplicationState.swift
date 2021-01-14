//
//  UploadApplicationState.swift
//  iosApp
//
//  Created by Nagy Robert on 10/12/2020.
//  Copyright © 2020 Halcyon Mobile. All rights reserved.
//

import Foundation
import common

class UploadApplicationState: ObservableObject {
    
    let categoryId: Int64
    let viewModel: UploadApplicationViewModel
    
    @Published var application: UploadApplicationUiModel?
    @Published var state = UploadApplicationViewModel.State.normal
    @Published var event: UploadApplicationViewModel.Event?
    
    init(categoryId: Int64){
        self.categoryId = categoryId
        viewModel = UploadApplicationViewModel(initialCategoryId: categoryId)
        
        viewModel.observeApplication{ application in
            self.application = application
        }
        viewModel.observeState{ state in
            self.state = state
        }
        viewModel.observeEvent{ event in
            self.event = event
        }
    }
}
