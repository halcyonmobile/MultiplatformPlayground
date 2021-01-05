//
//  App_PortfolioApp.swift
//  Shared
//
//  Created by Botond Magyarosi on 05.01.2021.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import common

@main
struct App_PortfolioApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

class AppDelegate: UIResponder, UIApplicationDelegate {
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application
        CommonModuleKt.doInitKoin()
        return true
    }
}

