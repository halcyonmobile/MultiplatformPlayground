//
//  FilePicker.swift
//  App Portfolio (macOS)
//
//  Created by Nagy Robert on 01.04.2021.
//  Copyright © 2021 Halcyon Mobile. All rights reserved.
//

import SwiftUI

func showMacOSImagePicker(onImageSelected: @escaping ((NSImage?) -> Void)){
    NSOpenPanel.openImage { result in
        if case let .success(image) = result {
            onImageSelected(image)
        }
    }
}

extension NSOpenPanel {
    
    static func openImage(completion: @escaping (_ result: Result<NSImage, Error>) -> ()) {
        let panel = NSOpenPanel()
        panel.allowsMultipleSelection = false
        panel.canChooseFiles = true
        panel.canChooseDirectories = false
        panel.allowedFileTypes = ["jpg", "jpeg", "png", "heic"]
        panel.canChooseFiles = true
        panel.begin { (result) in
            if result == .OK,
               let url = panel.urls.first,
               let image = NSImage(contentsOf: url) {
                completion(.success(image))
            } else {
                completion(.failure(
                    NSError(domain: "", code: 0, userInfo: [NSLocalizedDescriptionKey: "Failed to get file location"])
                ))
            }
        }
    }
}
