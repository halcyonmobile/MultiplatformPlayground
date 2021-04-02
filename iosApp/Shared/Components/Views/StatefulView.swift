//
//  StatefulView.swift
//  App Portfolio
//
//  Created by Botond Magyarosi on 14.01.2021.
//  Copyright © 2021 Halcyon Mobile. All rights reserved.
//

import SwiftUI

enum ViewState {
    case loading
    case error
    case empty
    case idle
}

struct StatefulView<Content, ErrorContent, EmptyContent>: View where Content: View, ErrorContent: View, EmptyContent: View {
    private let state: ViewState
    private let error: () -> ErrorContent
    private let empty: () -> EmptyContent
    private let content: () -> Content
    
    init(
        state: ViewState,
        @ViewBuilder error: @escaping () -> ErrorContent,
        @ViewBuilder empty: @escaping () -> EmptyContent,
        @ViewBuilder content: @escaping () -> Content
    ) {
        self.state = state
        self.error = error
        self.empty = empty
        self.content = content
    }
    
    var body: some View {
        switch state {
        case .loading: ProgressView()
        case .error: error()
        case .empty: empty()
        case .idle: content()
        }
    }
}

struct StatefulView_Previews: PreviewProvider {
    
    static var previews: some View {
        Group {
            preview(for: .loading)
            preview(for: .error)
            preview(for: .empty)
            preview(for: .idle)
        }
    }
    
    private static func preview(for state: ViewState) -> some View {
        StatefulView(state: state, error: { Text("Error") }, empty: { Text("Empty") }, content: { Text("This is content") })
    }
}
