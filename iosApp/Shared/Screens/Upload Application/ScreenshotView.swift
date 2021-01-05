//
//  ScreenshotView.swift
//  iosApp
//
//  Created by Nagy Robert on 10/12/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI

struct ScreenshotView: View {
    let image: Image

    var body: some View {
        image
            .resizable()
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .aspectRatio(1, contentMode: .fill)
            .clipShape(RoundedRectangle(cornerRadius: 5))
    }
}
