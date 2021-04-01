//
//  ImagePicker.swift
//  iosApp
//
//  Created by Nagy Robert on 10/12/2020.
//  Copyright © 2020 Halcyon Mobile. All rights reserved.
//

import SwiftUI

// https://www.hackingwithswift.com/books/ios-swiftui/importing-an-image-into-swiftui-using-uiimagepickercontroller

struct ImagePickerIOS: UIViewControllerRepresentable {
    @Environment(\.presentationMode) var presentationMode
    var onImageSelected: ((UIImage) -> ())

    func makeUIViewController(context: UIViewControllerRepresentableContext<ImagePickerIOS>) -> UIImagePickerController {
        let picker = UIImagePickerController()
        picker.delegate = context.coordinator
        return picker
    }

    func updateUIViewController(_ uiViewController: UIImagePickerController, context: UIViewControllerRepresentableContext<ImagePickerIOS>) {

    }

    func makeCoordinator() -> Coordinator {
        Coordinator(self)
    }

    class Coordinator: NSObject, UINavigationControllerDelegate, UIImagePickerControllerDelegate {
        let parent: ImagePickerIOS

        init(_ parent: ImagePickerIOS) {
            self.parent = parent
        }

        func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info: [UIImagePickerController.InfoKey: Any]) {
            if let uiImage = info[.originalImage] as? UIImage {
                parent.onImageSelected(uiImage)
            }

            parent.presentationMode.wrappedValue.dismiss()
        }
    }
}
