//
//  MokoResourceExtensions.swift
//  iosApp
//
//  Created by Nagy Robert on 07/12/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import common

extension ResourcesStringResource {
    
    func localize() -> String{
        return ResourcesResourceStringDesc(stringRes: self).localized()
    }
}
