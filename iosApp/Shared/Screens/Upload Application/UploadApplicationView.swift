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
    @Environment(\.presentationMode) var presentationMode
    
    @ObservedObject var state: UploadApplicationState
    @State private var showingImagePicker = false
    
    init(categoryId: Int64) {
        self.categoryId = categoryId
        state = UploadApplicationState(categoryId: categoryId)
    }

    #if os(macOS)
    let width: CGFloat? = 640
    #else
    let width: CGFloat? = nil
    #endif

    #if os(macOS)
    let height: CGFloat? = 480
    #else
    let height: CGFloat? = nil
    #endif

    var body: some View {
        switch state.state {
        case UploadApplicationViewModel.State.loading:
            VStack {
                ProgressView().frame(alignment: .center)
            }
        default:
            ScrollView {
                IconView(image: Binding(
                    get: { state.application?.icon },
                    set: {
                        if let icon = $0 {
                            state.viewModel.onIconChanged(icon: icon)
                        }
                    }
                )).padding(16)
                VStack(alignment:.leading) {
                    Text(MR.strings().screenshots.localize())
                        .font(.headline)
                    
                    LazyVGrid(columns: [GridItem(.adaptive(minimum: 80, maximum: 88), spacing: 8)], alignment: .leading, spacing: 8) {
                        ForEach(state.application?.screenshots ?? [], id: \.self) { image in
                            #if os(iOS)
                            ScreenshotView(image: Image(uiImage: image))
                            #elseif os(macOS)
                            ScreenshotView(image: Image(nsImage: image))
                            #endif
                        }
                        
                        ScreenshotUploadButton()
                            .onTapGesture {
                                #if os(iOS)
                                showingImagePicker.toggle()
                                #elseif os(macOS)
                                showMacOSImagePicker{ img in
                                    if let image = img {
                                        state.viewModel.onAddScreenshot(screenshot: image)
                                    }
                                }
                                #endif
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
                            }
                            HStack{
                                Button("Cancel", action: { presentationMode.wrappedValue.dismiss() })
                                Button("Submit", action: { state.viewModel.submit() }).padding(.horizontal, 8)
                            }.padding(.vertical, 8)
                        }
                    }
                }.sheet(isPresented: $showingImagePicker) {
                    #if os(iOS)
                    ImagePickerIOS(onImageSelected: {
                        state.viewModel.onAddScreenshot(screenshot: $0)
                    })
                    #endif
                }
                .padding(24)
                .frame(minWidth: width, minHeight: height)
            }
        }
    }
}

struct UploadApplicationView_Previews: PreviewProvider {
    
    static var previews: some View {
        UploadApplicationView(categoryId: 0)
    }
}
