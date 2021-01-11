//
//  ApplicationDetailView.swift
//  iosApp
//
//  Created by Nagy Robert on 08/12/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI
import common
import struct Kingfisher.KFImage

struct ApplicationDetailView: View {
    
    let applicationId: Int64
    @ObservedObject var state: ApplicationDetailState
    
    init(applicationId: Int64) {
        self.applicationId = applicationId
        state = ApplicationDetailState(applicationId: applicationId)
    }
    
    var body: some View {
        switch state.state {
        case ApplicationDetailViewModel.State.loading:
            VStack{
                Spacer()
                ProgressView()
                Spacer()
            }
        case ApplicationDetailViewModel.State.error:
            VStack{
                Spacer()
                Text(MR.strings().general_error.localize())
                    .multilineTextAlignment(.center)
                Button(MR.strings().retry.localize(), action: {
                    state.viewModel.loadDetail()
                }).padding(.top, 8)
                Spacer()
            }
        default:
            ScrollView{
                ZStack(alignment: .bottom){
                    VStack{
                        Header(imageUrl: state.applicationDetail!.icon, name: state.applicationDetail!.name, developer: state.applicationDetail!.developer)
                        HStack{
                            Property(name: MR.strings().rating.localize(), value: String(state.applicationDetail!.rating), icon: "star.fill")
                            Spacer()
                            Property(name: MR.strings().downloads.localize(), value: String(state.applicationDetail!.downloads), icon: "icloud.and.arrow.down")
                        }.padding(.top, 32)
                        Description(description: state.applicationDetail!.description_)
                            .padding(.top, 8)
                        
                        // TODO add screenshots
                    }.frame(alignment: .topLeading)
                    .padding(16)
                    Button((state.applicationDetail?.favourite ?? false) ? MR.strings().remove_from_favourites.localize() : MR.strings().add_to_favourites.localize(), action: {
                        state.viewModel.updateFavourite()
                    }).buttonStyle(PrimaryActionButtonStyle())
                    .alignmentGuide(.bottom) { d in d[.bottom] - 64 }
                }
            }
        }
    }
}

private struct Description: View{
    
    let description: String
    @State private var showMore = false
    
    var body: some View {
        VStack{
            Text(MR.strings().description_.localize())
                .font(.title)
            Text(description)
                .font(.caption)
                .lineLimit(showMore ? 100 : 3)
                .padding(8)
            Button(!showMore ? MR.strings().show_more.localize() : MR.strings().show_less.localize(), action:  {
                showMore = !showMore
            })
        }
    }
}

private struct Header: View{
    
    let imageUrl: String
    let name: String
    let developer: String
    let category: String = "Update this" // todo update this
    
    var body: some View{
        HStack{
            KFImage(URL(string: imageUrl))
                .cornerRadius(8)
                .aspectRatio(contentMode: .fill)
                .frame(width: 104,height: 104, alignment: .topLeading)
                .clipped()
            VStack{
                Text(name)
                    .font(.title3)
                Text(developer)
                    .font(.caption)
                Text(category)
                    .font(.caption)
                Spacer()
            }.frame(alignment: .topLeading)
            .padding(.leading, 8)
            Spacer()
        }
    }
}

private struct Property: View{
    
    let name: String
    let value: String
    let icon: String
    
    var body: some View{
        HStack{
            Image(systemName: icon)
                .foregroundColor(Color(ApplicationColors.accentColor))
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
