//
//  FavoritesView.swift
//  iosApp
//
//  Created by Zsolt Boldizsar on 9/10/20.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI
import common

struct FavoritesView: View {
    
    @ObservedObject var state: FavoritesState
    
    init() {
        state = FavoritesState()
    }
    
    var body: some View {
        VStack{
            switch state.state {
            case FavouritesViewModel.State.loading:
                VStack{
                    Spacer()
                    ProgressView()
                    Spacer()
                }
            case FavouritesViewModel.State.error:
                VStack{
                    Spacer()
                    Text(LocalizationsKt.generalError.localized())
                        .multilineTextAlignment(.center)
                    Button(LocalizationsKt.retry.localized(), action: {
                        state.viewModel.loadFavourites()
                    }).padding(.top, 8)
                    Spacer()
                }
            default:
                List(state.favourites, id: \.id){ favourite in
                    ApplicationView(application: favourite)
                }
            }
        }.navigationBarTitle(LocalizationsKt.favourites.localized())
    }
}

struct FavoritesView_Previews: PreviewProvider {
    static var previews: some View {
        FavoritesView()
    }
}
