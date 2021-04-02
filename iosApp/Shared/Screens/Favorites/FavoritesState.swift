//
//  FavoritesState.swift
//  iosApp
//
//  Created by Nagy Robert on 30/11/2020.
//  Copyright © 2020 Halcyon Mobile. All rights reserved.
//

import Foundation
import common

class FavoritesState: ObservableObject{
    
    let viewModel = FavouritesViewModel()
    @Published private(set) var favourites: [ApplicationUiModel.App] = []
    @Published private(set) var state: ViewState = .idle
    
    init() {
        viewModel.observeFavourites { favourites in
            self.favourites = favourites
        }
        viewModel.observeState { state in
            self.state = state.toViewState()
        }
    }
    
    deinit {
        viewModel.dispose()
    }
}

private extension FavouritesViewModel.State {
    
    func toViewState() -> ViewState {
        switch self {
        case .loading: return .loading
        case .error: return .error
        case .normal: return .idle
        default: return .idle
        }
    }
}
