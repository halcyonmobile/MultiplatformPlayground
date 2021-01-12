//
//  ApplicationView.swift
//  iosApp
//
//  Created by Nagy Robert on 04/11/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI
import common
//import Kingfisher
//import struct Kingfisher.KFImage

struct ApplicationView: View {
    
    var application: ApplicationUiModel.App
    
    var body: some View {
        HStack(alignment: .center) {
//            KFImage(URL(string: application.icon))
//                .frame(width: 64, height: 64, alignment: .center)
//                .cornerRadius(8)
//                .padding(.trailing)
            VStack(alignment: .leading) {
                Text(application.name)
                    .font(.body)
                Text(application.developer)
                    .font(.caption)
                HStack{
                    Text(application.rating.description)
                        .font(.caption)
                    Image(systemName: "star.fill")
                        .foregroundColor(.accentColor)
                }
            }
        }
    }
}

struct ApplicationView_Previews: PreviewProvider {
    static var previews: some View {
        ApplicationView(
            application: ApplicationUiModel.App(
                id: 0,
                name: "Application 1",
                icon: "https://picsum.photos/100/100",
                developer: "Developer XY",
                rating: 4.5,
                favourite: false,
                categoryId: 1
            ))
    }
}
