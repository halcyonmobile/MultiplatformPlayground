//
//  UploadApplicationView.swift
//  iosApp
//
//  Created by Nagy Robert on 03/11/2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI
import common

struct UploadApplicationView: View {
    
    let categoryId: Int64
    @ObservedObject var state: UploadApplicationState
    @State private var showingImagePicker = false
    
    init(categoryId: Int64) {
        self.categoryId = categoryId
        state = UploadApplicationState(categoryId: categoryId)
    }
    
    var body: some View {
        NavigationView {
            switch state.state{
            case UploadApplicationViewModel.State.loading:
                VStack{
                    ProgressView().frame(alignment: .center)
                }
            default:
                ScrollView {
                    IconView(image: Binding(
                        get: { state.application?.icon},
                        set: {
                            if let icon = $0{
                                state.viewModel.onIconChanged(icon: icon)
                            }
                        }
                    ))
                    
                    VStack(alignment:.leading){
                        Text(MR.strings().screenshots.localize())
                            .font(.headline)
                        
                        LazyVGrid(columns: [GridItem(.adaptive(minimum: 80, maximum: 88), spacing: 8)], alignment: .leading, spacing: 8) {
                            ForEach(state.application?.screenshots ?? [], id: \.self) { image in
                                ScreenshotView(image: Image(uiImage: image))
                            }
                            
                            ScreenshotUploadButton()
                                .onTapGesture {
                                    showingImagePicker.toggle()
                                }
                        }
                        
                        Text(MR.strings().application_details.localize())
                            .font(.headline)
                        HStack{
                            Image(systemName: "bookmark.fill")
                                .foregroundColor(Color(ApplicationColors.accentColor))
                            TextField(MR.strings().application_name.localize(), text: Binding(
                                get: { state.application?.name ?? "" },
                                set: { state.viewModel.onNameChanged(name: $0) }
                            ))
                            .padding(.leading, 8)
                        }.padding(8)
                        HStack{
                            Image(systemName: "person.2.fill")
                                .foregroundColor(Color(ApplicationColors.accentColor))
                            TextField(MR.strings().developer.localize(), text: Binding(
                                get: { state.application?.developer ?? "" },
                                set: { state.viewModel.onDeveloperChanged(developer: $0)}
                            ))
                            .padding(2)
                        }.padding(.leading, 4)
                        .padding(.top, 8)
                        .padding(.bottom, 8)
                        .padding(.trailing, 8)
                        HStack{
                            Image(systemName: "pencil")
                                .foregroundColor(Color(ApplicationColors.accentColor))
                            TextField(MR.strings().description_.localize(), text: Binding(
                                get: { state.application?.description_ ?? "" },
                                set: { state.viewModel.onDescriptionChanged(description: $0)}
                            ))
                            .padding(.leading, 6)
                        }.padding(8)
                        HStack{
                            Image(systemName: "square.and.arrow.down")
                                .foregroundColor(Color(ApplicationColors.accentColor))
                            TextField(MR.strings().downloads.localize(), text: Binding(
                                get: { state.application?.downloads ?? "" },
                                set: { state.viewModel.onDownloadsChanged(downloads: $0)}
                            ))
                            .padding(.leading, 6)
                            
                            Image(systemName: "star.fill")
                                .foregroundColor(Color(ApplicationColors.accentColor))
                            TextField(MR.strings().rating.localize(), text: Binding(
                                get: {
                                    if let rating = state.application?.rating?.floatValue{
                                        return String(describing: rating)
                                    } else {
                                        return ""
                                    }
                                },
                                set: { state.viewModel.onRatingChanged(rating: $0)}
                            ))
                            .keyboardType(.decimalPad)
                        }.padding(8)
                        
                        Spacer()
                    }.sheet(isPresented: $showingImagePicker) {
                        ImagePicker(onImageSelected: {
                            state.viewModel.onAddScreenshot(screenshot: $0)
                        })
                    }
                    .padding(.horizontal, 24)
                    .navigationBarTitle(MR.strings().upload_title.localize())
                    .navigationBarItems(trailing:
                                            Button {
                                                state.viewModel.submit()
                                            } label: {
                                                Image(systemName: "checkmark").font(.system(size: 24, weight: .light))
                                            }
                    )
                }
            }
        }
    }
}
