//
//  UploadApplicationView.swift
//  iosApp
//
//  Created by Nagy Robert on 03/11/2020.
//  Copyright © 2020 Halcyon Mobile. All rights reserved.
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
            switch state.state {
            case UploadApplicationViewModel.State.loading:
                VStack {
                    ProgressView().frame(alignment: .center)
                }
            default:
                ScrollView {
//                    IconView(image: Binding(
//                        get: { state.application?.icon },
//                        set: {
//                            if let icon = $0 {
//                                state.viewModel.onIconChanged(icon: icon)
//                            }
//                        }
//                    ))
                    
                    VStack(alignment:.leading) {
                        Text(MR.strings().screenshots.localize())
                            .font(.headline)
                        
                        LazyVGrid(columns: [GridItem(.adaptive(minimum: 80, maximum: 88), spacing: 8)], alignment: .leading, spacing: 8) {
//                            ForEach(state.application?.screenshots ?? [], id: \.self) { image in
//                                ScreenshotView(image: Image(uiImage: image))
//                            }
                            
                            ScreenshotUploadButton()
                                .onTapGesture {
                                    showingImagePicker.toggle()
                                }
                        }
                        
                        Section(header: Text(MR.strings().application_details.localize()).font(.largeTitle)) {
                            VStack(spacing: 16) {
                                TextField(MR.strings().application_name.localize(), text: Binding(
                                    get: { state.application?.name ?? "" },
                                    set: { state.viewModel.onNameChanged(name: $0) }
                                ))
                                .imagePrefix(systemImageName: "bookmark.fill")
                                
                                TextField(MR.strings().developer.localize(), text: Binding(
                                    get: { state.application?.developer ?? "" },
                                    set: { state.viewModel.onDeveloperChanged(developer: $0)}
                                ))
                                .imagePrefix(systemImageName: "person.2.fill")
                                
                                TextField(MR.strings().description_.localize(), text: Binding(
                                    get: { state.application?.description_ ?? "" },
                                    set: { state.viewModel.onDescriptionChanged(description: $0)}
                                ))
                                .imagePrefix(systemImageName: "pencil")
                                
                                HStack(spacing: 16) {
                                    TextField(MR.strings().downloads.localize(), text: Binding(
                                        get: { state.application?.downloads ?? "" },
                                        set: { state.viewModel.onDownloadsChanged(downloads: $0)}
                                    ))
                                    .imagePrefix(systemImageName: "square.and.arrow.down")
                                    
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
                                    .imagePrefix(systemImageName: "star.fill")
//                                    .keyboardType(.decimalPad)
                                }
                            }
                        }
                    }.sheet(isPresented: $showingImagePicker) {
                        #if os(iOS)
                        ImagePicker(onImageSelected: {
                            state.viewModel.onAddScreenshot(screenshot: $0)
                        })
                        #endif
                    }
                    .padding(.horizontal, 24)
//                    .navigationBarTitle(MR.strings().upload_title.localize())
                    .toolbar {
                        ToolbarItem(placement: .principal) {
                            Button("Save", action: state.viewModel.submit)
                        }
                    }
                }
            }
        }
    }
}

struct UploadApplicationView_Previews: PreviewProvider {
    
    static var previews: some View {
        UploadApplicationView(categoryId: 0)
    }
}
