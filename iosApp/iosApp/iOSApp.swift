import SwiftUI

@main
struct iOSApp: App {
    // Create an instance of AppDelegate
    @UIApplicationDelegateAdaptor(AppDelegate.self) var appDelegate

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
