//
//  IconView.swift
//  iosApp
//
//  Created by Nagy Robert on 10/12/2020.
//  Copyright © 2020 Halcyon Mobile. All rights reserved.
//

import SwiftUI
#if os(macOS)
import Quartz
#endif

#if os(macOS)
typealias UIImage = NSImage
#endif

struct IconView: View {
    @Binding var image: UIImage?
    @State private var showingImagePicker = false
    
    var body: some View {
        if let image = image {
            #if os(iOS)
            Image(uiImage: image)
                .resizable()
                .frame(width: 100, height: 100)
                .clipShape(Circle())
            #elseif os(macOS)
            Image(nsImage: image)
                .resizable()
                .frame(width: 100, height: 100)
                .clipShape(Circle())
            #endif
        } else {
            Image(systemName: "camera.circle.fill")
                .font(.system(size: 28, weight: .light))
                .foregroundColor(Color(#colorLiteral(red: 0.4352535307, green: 0.4353201389, blue: 0.4352389574, alpha: 1)))
                .frame(width: 100, height: 100)
                .background(Color(#colorLiteral(red: 0.9386131763, green: 0.9536930919, blue: 0.9635006785, alpha: 1)))
                .clipShape(Circle())
                .sheet(isPresented: $showingImagePicker){
                    #if os(iOS)
                    ImagePickerIOS(onImageSelected: {
                        self.image = $0
                    })
                    #endif
                }
                .onTapGesture {
                    #if os(iOS)
                    showingImagePicker.toggle()
                    #elseif os(macOS)
                    showMacOSImagePicker{ image in
                        self.image = image
                    }
                    #endif
                }
        }
    }
}
