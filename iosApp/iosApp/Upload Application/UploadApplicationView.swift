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
    private let viewModel: UploadApplicationViewModel
    
    
    init(categoryId: Int64) {
        self.categoryId = categoryId
        viewModel = UploadApplicationViewModel(initialCategoryId: categoryId)
    }
    
    @State private var applicationName = ""
    @State private var developer = ""
    @State private var description = ""
    @State private var downloads = ""
    @State private var rating = ""
    
    var body: some View {
        VStack(alignment:.leading){
            //            TODO implement screenshots
            Text(LocalizationsKt.applicationDetails.localized())
                .font(.headline)
            HStack{
                Image(systemName: "bookmark.fill")
                    .foregroundColor(Color(ApplicationColors.accentColor))
                TextField(LocalizationsKt.applicationName.localized(), text: $applicationName, onCommit: {
                    viewModel.onNameChanged(name: applicationName)
                })
                .padding(.leading, 8)
            }.padding(8)
            HStack{
                Image(systemName: "person.2.fill")
                    .foregroundColor(Color(ApplicationColors.accentColor))
                TextField(LocalizationsKt.developer.localized(), text: $developer, onCommit: {
                    viewModel.onDeveloperChanged(developer: developer)
                })
                .padding(2)
            }.padding(.leading, 4)
            .padding(.top, 8)
            .padding(.bottom, 8)
            .padding(.trailing, 8)
            HStack{
                Image(systemName: "pencil")
                    .foregroundColor(Color(ApplicationColors.accentColor))
                TextField(LocalizationsKt.appDescription.localized(), text: $description, onCommit: {
                    viewModel.onDescriptionChanged(description: description)
                })
                .padding(.leading, 6)
            }.padding(8)
            HStack{
                Image(systemName: "square.and.arrow.down")
                    .foregroundColor(Color(ApplicationColors.accentColor))
                TextField(LocalizationsKt.downloads.localized(), text: $downloads, onCommit: {
                    viewModel.onDownloadsChanged(downloads: downloads)
                })
                .padding(.leading, 6)
                
                Image(systemName: "star.fill")
                    .foregroundColor(Color(ApplicationColors.accentColor))
                TextField(LocalizationsKt.rating.localized(), text: $rating, onCommit: {
                    viewModel.onRatingChanged(rating: rating)
                })
                .keyboardType(.decimalPad)
            }.padding(8)
            
            Spacer()
        }.padding(16)
        .navigationTitle(LocalizationsKt.uploadTitle.localized())
    }
}
