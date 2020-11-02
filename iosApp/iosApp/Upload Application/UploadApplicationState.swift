//
//  UploadApplicationState.swift
//  iosApp
//
//  Created by Nagy Robert on 02/11/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import common

class UploadApplicationState: ObservableObject{
 
    let categoryId: Int64
    let viewModel: UploadApplicationViewModel
    
    init(categoryId: Int64) {
        self.categoryId = categoryId
        self.viewModel = ServiceLocator().getUploadApplicationViewModel(categoryId: categoryId)
    }
}
