//
//  PrimaryActionButtonStyle.swift
//  iosApp
//
//  Created by Nagy Robert on 11/12/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct PrimaryActionButtonStyle: ButtonStyle {
    
    func makeBody(configuration: Self.Configuration) -> some View {
        configuration.label
            .padding(20)
            .background(
                ZStack {
                    RoundedRectangle(cornerRadius: 8, style: .continuous)
                        .blendMode(.overlay)
                    RoundedRectangle(cornerRadius: 8, style: .continuous)
                        .fill(Color(ApplicationColors.accentColor))
                }
        )
            .scaleEffect(configuration.isPressed ? 0.95: 1)
            .foregroundColor(.white)
            .animation(.spring())
    }
}
