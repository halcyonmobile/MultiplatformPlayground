//
//  PlaceholderView.swift
//  iosApp
//
//  Created by Botond Magyarosi on 05.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import common

struct PlaceholderView: View {
    let message: String
    let retryAction: (() -> Void)?
    
    internal init(message: String, retryAction: (() -> Void)? = nil) {
        self.message = message
        self.retryAction = retryAction
    }
    
    var body: some View {
        VStack(spacing: 8) {
            Spacer()
            Text(message)
                .multilineTextAlignment(.center)
            if let retryAction = retryAction {
                Button(MR.strings().retry.localize(), action: retryAction)
            }
            Spacer()
        }
        .padding()
    }
}

struct PlaceholderView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            PlaceholderView(message: "Test title")
            PlaceholderView(message: "Test title", retryAction: { })
            
        }
    }
}
