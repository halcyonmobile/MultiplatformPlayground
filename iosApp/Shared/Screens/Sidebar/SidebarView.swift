//
//  SidebarView.swift
//  App Portfolio (iOS)
//
//  Created by Botond Magyarosi on 05.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI

enum Categories: String, CaseIterable {
    case work = "Work"
    case creativity = "Creativity"
}

struct SidebarView: View {
    @State var selectedFolder: String?
    
    var body: some View {
        NavigationView {
            List(selection: $selectedFolder) {
                NavigationLink(destination: Text("Detail"), label: { Label("Favorites", systemImage: "hearth") })
                Section(header: Text("Categories")) {
                    ForEach(Categories.allCases, id: \.self) { folder in
                        NavigationLink(
                            destination: Text("Detail")
                        ) {
                            Text(folder.rawValue).font(.headline)
                        }
                    }
                }
            }.listStyle(SidebarListStyle())
        }
    }
}

struct SidebarView_Previews: PreviewProvider {
    static var previews: some View {
        SidebarView()
    }
}
