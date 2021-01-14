//
//  FavoritesView.swift
//  iosApp
//
//  Created by Zsolt Boldizsar on 9/10/20.
//  Copyright © 2020 Halcyon Mobile. All rights reserved.
//

import SwiftUI
import common

struct FavoritesView: View {
    
    @ObservedObject var state: FavoritesState
    
    init() {
        state = FavoritesState()
    }
    
    var body: some View {
        NavigationView {
            StatefulView(state: state.state, error: {
                PlaceholderView(message: MR.strings().general_error.localize()) {
                    state.viewModel.loadFavourites()
                }
            }, empty: {
                EmptyView()
            }, content: {
                List(state.favourites, id: \.id){ favourite in
                    NavigationLink(destination: ApplicationDetailView(applicationId: favourite.id)){
                        ApplicationView(application: favourite)
                    }
                }
            })
            .navigationTitle(MR.strings().favourites.localize())
        }
    }
}

struct FavoritesView_Previews: PreviewProvider {
    
    static var previews: some View {
        FavoritesView()
    }
}
