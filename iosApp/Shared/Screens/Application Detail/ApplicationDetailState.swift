//
//  ApplicationDetailState.swift
//  iosApp
//
//  Created by Nagy Robert on 08/12/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import common

class ApplicationDetailState: ObservableObject{
    
    let applicationId: Int64
    let viewModel: ApplicationDetailViewModel
    
    @Published private(set) var applicationDetail: ApplicationDetailUiModel? = nil
    @Published private(set) var state = ApplicationDetailViewModel.State.loading
    
    init(applicationId: Int64) {
        self.applicationId = applicationId
        viewModel = ApplicationDetailViewModel(applicationId: applicationId)
        
        viewModel.observeApplicationDetail{ applicationDetail in
            self.applicationDetail = applicationDetail
        }
        viewModel.observeState { state in
            self.state = state
        }
        viewModel.observeEvent{ event in
            // TODO handle events
        }
    }
    
    deinit {
        viewModel.dispose()
    }
}
