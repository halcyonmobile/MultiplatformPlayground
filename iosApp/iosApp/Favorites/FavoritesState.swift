//
//  FavoritesState.swift
//  iosApp
//
//  Created by Nagy Robert on 30/11/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import Foundation
import common

class FavoritesState: ObservableObject{
    
    let viewModel = FavouritesViewModel()
    @Published private(set) var favourites = [ApplicationUiModel.App]()
    @Published private(set) var state: FavouritesViewModel.State = FavouritesViewModel.State.normal
    
    init(){
        viewModel.observeFavourites{ favourites in
            self.favourites = favourites
        }
        viewModel.observeState{ state in
            self.state = state
        }
    }
    
    deinit {
        viewModel.dispose()
    }
}
