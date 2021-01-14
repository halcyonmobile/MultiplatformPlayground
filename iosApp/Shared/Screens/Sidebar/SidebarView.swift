//
//  SidebarView.swift
//  App Portfolio (iOS)
//
//  Created by Botond Magyarosi on 05.01.2021.
//  Copyright © 2021 Halcyon Mobile. All rights reserved.
//

import SwiftUI
import common
import Kingfisher

struct SidebarView: View {
    @ObservedObject var state = SidebarViewModel()
    
    var body: some View {
        NavigationView {
            List {
                Spacer(minLength: 8)
                NavigationLink(destination: FavoritesView()) {
                    Label(MR.strings().favourites.localize(), systemImage: "heart.fill")
                }
                Spacer(minLength: 8)
                if !state.categories.isEmpty {
                    Section(header: Text(MR.strings().category.localize())) {
                        ForEach(state.categories, id: \.self) { category in
                            NavigationLink(destination: ApplicationsView(categoryId: category.id, title: category.name), label: {
                                HStack {
                                    KFImage(URL(string: category.icon))
                                        .renderingMode(.template)
                                    Text(category.name)
                                }
                            })
                        }
                    }
                } else {
                    ProgressView()
                }
            }
            .listStyle(SidebarListStyle())
            .navigationTitle(MR.strings().app_name.localize())
        }
    }
}

struct SidebarView_Previews: PreviewProvider {
    static var previews: some View {
        SidebarView()
    }
}
