//
//  IconView.swift
//  iosApp
//
//  Created by Nagy Robert on 10/12/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI

struct IconView: View {
    @Binding var image: UIImage?
    @State private var showingImagePicker = false
    
    var body: some View {
        Button(action: {
            showingImagePicker.toggle()
        }, label: {
            if let image = image {
                Image(uiImage: image)
                    .resizable()
                    .frame(width: 100, height: 100)
                    .clipShape(Circle())
            } else {
                Image(systemName: "camera.circle.fill")
                    .font(.system(size: 28, weight: .light))
                    .foregroundColor(Color(#colorLiteral(red: 0.4352535307, green: 0.4353201389, blue: 0.4352389574, alpha: 1)))
                    .frame(width: 100, height: 100)
                    .background(Color(#colorLiteral(red: 0.9386131763, green: 0.9536930919, blue: 0.9635006785, alpha: 1)))
                    .clipShape(Circle())
            }
        })
        .sheet(isPresented: $showingImagePicker) {
            ImagePicker(onImageSelected: {
                self.image = $0
            })
        }
    }
}
