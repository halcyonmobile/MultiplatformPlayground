//
//  IconPrefixViewModifier.swift
//  App Portfolio (iOS)
//
//  Created by Botond Magyarosi on 14.01.2021.
//  Copyright © 2021 Halcyon Mobile. All rights reserved.
//

import SwiftUI

struct IconPrefixViewModifier: ViewModifier {
    
    let systemImageName: String
    
    func body(content: Content) -> some View {
        HStack {
            Image(systemName: systemImageName)
                .foregroundColor(.accentColor)
                .frame(width: 24, height: 24)
            content
        }
    }
}

extension TextField {
    
    func imagePrefix(systemImageName: String) -> some View {
        modifier(IconPrefixViewModifier(systemImageName: systemImageName))
    }
}
