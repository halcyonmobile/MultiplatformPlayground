//
//  FloatingActionButton.swift
//  iosApp
//
//  Created by Nagy Robert on 02/11/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI

struct FloatingActionButton: View {
    var icon: String
    var action: () -> Void
    
    var body: some View {
        Button(action: action){
            Image(systemName: icon)
                .resizable()
                .frame(width: 48, height: 48)
                .foregroundColor(.accentColor)
                .shadow(color: .gray, radius: 0.2, x: 1, y: 1)
        }
    }
}

struct FloatingActionButton_Previews: PreviewProvider {
    static var previews: some View {
        FloatingActionButton(icon: "plus.circle.fill"){}
    }
}
