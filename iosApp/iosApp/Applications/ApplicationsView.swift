//
//  ApplicationsView.swift
//  iosApp
//
//  Created by Nagy Robert on 04/11/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI
import common

struct ApplicationsView: View {
    
    let categoryId: Int64
    @ObservedObject var state: ApplicationsState
    
    init(categoryId: Int64) {
        self.categoryId =  categoryId
        state = ApplicationsState(categoryId: categoryId)
    }
    
    var body: some View {
        if(state.isLoading){
            ProgressView()
                .frame(alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/)
        }
        List(state.applications, id: \.id){ application in
            ApplicationView(application: application)
        }
    }
}
