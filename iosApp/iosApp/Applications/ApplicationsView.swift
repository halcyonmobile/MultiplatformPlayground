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
        // TODO add loading to last item of applications
        if(state.isLoading){
            VStack{
                Spacer()
                ProgressView()
                Spacer()
            }
        }else {
            switch state.state {
            case ApplicationsViewModel.State.error:
                VStack{
                    Spacer()
                    Text(MR.strings().general_error.localize())
                        .multilineTextAlignment(.center)
                    Spacer()
                }
            case ApplicationsViewModel.State.empty:
                VStack{
                    Spacer()
                    Text(MR.strings().applications_empty_message.localize())
                        .multilineTextAlignment(.center)
                    Spacer()
                }
            default:
                List(state.applications, id: \.id){ application in
                    ApplicationView(application: application)
                }
            }
           
        }
    }
}
