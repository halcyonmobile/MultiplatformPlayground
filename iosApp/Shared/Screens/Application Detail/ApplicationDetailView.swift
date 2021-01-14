//
//  ApplicationDetailView.swift
//  iosApp
//
//  Created by Nagy Robert on 08/12/2020.
//  Copyright © 2020 Halcyon Mobile. All rights reserved.
//

import SwiftUI
import common
import Kingfisher

struct ApplicationDetailView: View {
    
    let applicationId: Int64
    @ObservedObject var state: ApplicationDetailState
    
    init(applicationId: Int64) {
        self.applicationId = applicationId
        state = ApplicationDetailState(applicationId: applicationId)
    }
    
    var body: some View {
        StatefulView(state: state.state, error: {
            PlaceholderView(message: MR.strings().general_error.localize()) {
                state.viewModel.loadDetail()
            }
        }, empty: {
            EmptyView()
        }, content: {
            ScrollView {
                VStack(alignment: .leading, spacing: 16) {
                    header
                    
                    #if os(iOS)
                    if UIDevice.current.userInterfaceIdiom == .phone {
                        ratings
                    }
                    #endif
                    
                    ScrollView(.horizontal, showsIndicators: true, content: {
                        LazyHStack {
                            ForEach(state.applicationDetail!.screenshots, id: \.id, content: { screenshot in
                                KFImage(URL(string: screenshot.image))
                                    .resizable()
                                    .aspectRatio(contentMode: .fit)
                            })
                        }
                    })
                    .frame(height: 250)
                    
                    Description(description: state.applicationDetail!.description_)
                }
                .padding()
            }
            
        })
//        #if os(iOS)
//        .navigationBarTitleDisplayMode(.inline)
//        #endif
        .toolbar {
            ToolbarItem(placement: .principal) {
                Button(action: { state.viewModel.updateFavourite() }) {
                    Image(systemName: state.applicationDetail?.favourite ?? false ? "heart.fill" : "heart")
                }
            }
        }
    }
    
    var header: some View {
        HStack(alignment: .top) {
            KFImage(URL(string: state.applicationDetail!.icon))
                .resizable()
                .frame(maxWidth: 200, maxHeight: 200)
                .aspectRatio(1, contentMode: .fill)
                .cornerRadius(8)
            VStack(alignment: .leading) {
                Text(state.applicationDetail!.name)
                    .font(.title)
                Text(state.applicationDetail!.developer)
                    .font(.title3)
                Text("CHANGE_ME: CATEGORY")
                    .font(.caption)
                Spacer()
                #if os(iOS)
                if UIDevice.current.userInterfaceIdiom != .phone {
                    ratings
                }
                #else
                ratings
                #endif
                Spacer()
            }
            .padding(.leading)
            Spacer()
        }
    }
    
    private var ratings: some View {
        HStack(spacing: 32) {
            Property(name: MR.strings().rating.localize(), value: String(state.applicationDetail!.rating), icon: "star.fill")
            Property(name: MR.strings().downloads.localize(), value: String(state.applicationDetail!.downloads), icon: "icloud.and.arrow.down")
        }
    }
}

private struct Description: View{
    
    let description: String
    @State private var showMore = false
    
    var body: some View {
        VStack(alignment: .leading, spacing: 16) {
            Text(MR.strings().description_.localize())
                .font(.title)
            Text(description)
                .font(.body)
                .lineLimit(showMore ? nil : 3)
            Button(!showMore ? MR.strings().show_more.localize() : MR.strings().show_less.localize(), action:  {
                showMore = !showMore
            })
        }
    }
}

private struct Property: View {
    
    let name: String
    let value: String
    let icon: String
    
    var body: some View{
        HStack{
            Image(systemName: icon)
                .foregroundColor(.accentColor)
                .frame(alignment: .center)
                .padding(8)
            VStack {
                Text(value)
                    .font(/*@START_MENU_TOKEN@*/.title/*@END_MENU_TOKEN@*/)
                Text(name)
                    .font(.caption2)
            }
        }
    }
}

struct Description_Previews: PreviewProvider {
    static var previews: some View {
        Description(description: "Your next job is only one download away. \n" +
                        "From registration to employment, the RightStaff app helps you through the entire hiring process. We’ll guide you to a new medical job in just a few simple taps. All you will need to do is download the app, register your skills, fill in your availability, choose your area of work, update your location and you’re halfway to reciving your first shift.")
    }
}
