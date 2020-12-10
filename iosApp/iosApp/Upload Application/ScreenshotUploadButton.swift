//
//  ScreenshotUploadButton.swift
//  iosApp
//
//  Created by Nagy Robert on 10/12/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI

struct ScreenshotUploadButton: View {
    var body: some View {
        Image(systemName: "camera.circle.fill")
            .font(.system(size: 28, weight: .light))
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .foregroundColor(Color(#colorLiteral(red: 0.4352535307, green: 0.4353201389, blue: 0.4352389574, alpha: 1)))
            .background(Color(#colorLiteral(red: 0.9386131763, green: 0.9536930919, blue: 0.9635006785, alpha: 1)))
            .aspectRatio(1, contentMode: .fill)
            .overlay(
                RoundedRectangle(cornerRadius: 5.0)
                    .strokeBorder(style: StrokeStyle(lineWidth: 1, dash: [5]))
                    .foregroundColor(Color(#colorLiteral(red: 0.501960814, green: 0.501960814, blue: 0.501960814, alpha: 1)))
            )
    }
}

struct ScreenshotUploadButton_Previews: PreviewProvider {
    static var previews: some View {
        ScreenshotUploadButton()
    }
}
