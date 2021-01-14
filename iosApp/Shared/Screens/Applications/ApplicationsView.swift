//
//  ApplicationsView.swift
//  iosApp
//
//  Created by Nagy Robert on 04/11/2020.
//  Copyright © 2020 Halcyon Mobile. All rights reserved.
//

import SwiftUI
import common

struct ApplicationsView: View {
    
    let categoryId: Int64
    private let title: String
    @ObservedObject var state: ApplicationsState
    @State private var showsAddApplication = false
    
    init(categoryId: Int64, title: String = "") {
        self.categoryId =  categoryId
        self.title = title
        state = ApplicationsState(categoryId: categoryId)
    }
    
    var body: some View {
        StatefulView(state: state.state, error: {
            PlaceholderView(message: MR.strings().general_error.localize())
        }, empty: {
            PlaceholderView(message: MR.strings().applications_empty_message.localize())
        }, content: {
            List(state.items, id: \.id) { item in
                if let app = item as? ApplicationUiModel.App {
                    NavigationLink(destination: ApplicationDetailView(applicationId: app.id)) {
                        ApplicationView(application: app)
                    }.onAppear{
                        if(app.id == state.items.last?.id) {
                            state.viewModel.load()
                        }
                    }
                }
            }
            .listStyle(PlainListStyle())
        })
//        .navigationTitle(title)
        .sheet(isPresented: $showsAddApplication, content: { UploadApplicationView(categoryId: categoryId) })
        .toolbar {
            ToolbarItem(placement: .principal) {
                Button(action: { showsAddApplication.toggle() }, label: { Label("Add", systemImage: "plus.circle.fill") })
            }
        }
    }
}
