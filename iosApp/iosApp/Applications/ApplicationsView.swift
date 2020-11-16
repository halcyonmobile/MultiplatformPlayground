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
            VStack{
                Spacer()
                ProgressView()
                Spacer()
            }
        }else if state.applications.count > 0 {
            List(state.applications, id: \.id){ application in
                ApplicationView(application: application)
            }
        }else{
            VStack{
                Spacer()
                Text(LocalizationsKt.applicationsEmptyMessage.localized())
                    .multilineTextAlignment(.center)
                Spacer()
            }
        }
    }
}
