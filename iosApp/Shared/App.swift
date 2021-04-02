//
//  App_PortfolioApp.swift
//  Shared
//
//  Created by Botond Magyarosi on 05.01.2021.
//  Copyright © 2021 Halcyon Mobile. All rights reserved.
//

import SwiftUI
import common

@main
struct App_PortfolioApp: App {
    #if os(iOS)
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    #elseif os(macOS)
    @NSApplicationDelegateAdaptor(AppDelegate.self) var appDelegate
    #endif
 
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

#if os(iOS)
class AppDelegate: UIResponder, UIApplicationDelegate {
    
    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        // Override point for customization after application
        CommonModuleKt.doInitKoin()
        return true
    }
}
#elseif os(macOS)
class AppDelegate: NSResponder, NSApplicationDelegate {
    
    func applicationWillFinishLaunching(_ notification: Notification) {
        CommonModuleKt.doInitKoin()
    }
}
#endif
