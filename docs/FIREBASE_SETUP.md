Firebase — Setup & Troubleshooting (Android / iOS / Web)

Overview
This project uses Firebase across platforms via platform SDKs (Android: `com.google.firebase`, iOS: `Firebase` via CocoaPods/SwiftPM) and a small `expect/actual` wrapper in `shared/utils/FirebaseInitializer.*.kt` to expose token and topic APIs to the shared code.

Files to verify
- `androidApp/google-services.json` — Android config
- `iosApp/iosApp/GoogleService-Info.plist` — iOS config
- `shared/src/androidMain/kotlin/.../FirebaseInitializer.android.kt`
- `shared/src/iosMain/kotlin/.../FirebaseInitializer.ios.kt`
- `iosApp/iosApp/AppDelegate.swift` — iOS calls `FirebaseApp.configure()` and sets up `Messaging` delegate in this template.

Android setup checklist
1. Ensure `google-services.json` is present inside `androidApp/` and (optionally) `androidApp/src/debug/google-services.json` or `androidApp/src/release/google-services.json` if you have flavor-specific config.
2. Confirm `androidApp/build.gradle.kts` applies the `com.google.gms.google-services` plugin and includes the Firebase BOM and required artifacts. Example lines:
   - `implementation(platform("com.google.firebase:firebase-bom:<version>"))`
   - `implementation("com.google.firebase:firebase-messaging")`
3. Verify `initializeFirebase()` actual in `shared/src/androidMain` calls `FirebaseApp.initializeApp(appContext!!)` (this project uses that).
4. Run the Android app and check logcat for `Firebase registration token:` or for `Firebase App initialized` messages.

iOS setup checklist
1. Confirm `GoogleService-Info.plist` is added to Xcode project (`iosApp/iosApp`) and is included in the app target.
2. `AppDelegate.swift` should call `FirebaseApp.configure()` early in `didFinishLaunchingWithOptions` — this project already has that.
3. The `shared/src/iosMain` `initializeFirebase()` currently is a no-op because iOS initialization is performed in `AppDelegate`. That is acceptable; ensure shared code only depends on `getToken()` after iOS initialization completes.
4. For SwiftPM-based Firebase integration, confirm `Package.resolved` references `firebase-ios-sdk` (this project includes SwiftPM references). If you prefer CocoaPods, add Podspec config accordingly.

Web / JS
- If you plan to use Firebase features on the web/JS target, decide whether to use `dev.gitlive:firebase` KMP wrappers or platform-specific JS libs. This template includes `dev.gitlive` dependencies in metadata; choose a consistent approach and ensure initialization occurs before any token/topic calls.

Common runtime issues
- Missing platform config files (`google-services.json` or `GoogleService-Info.plist`) — app initializes but Firebase features fail silently.
- Token retrieval before initialization — ensure you call `initializeFirebase()` (or let platform AppDelegate/Android Application do the initialization) before calling `getToken()` in shared code.
- Dependencies mismatch between KMP `dev.gitlive` artifacts and native SDKs — prefer using the native SDKs with an `expect/actual` wrapper if you want maximum control.

Suggestions and best practice
- Keep platform initialization in the platform entry points (Android `Application` or `MainActivity` and iOS `AppDelegate`) and have `shared` rely on `getToken()`/`subscribeToTopic()` after initialization.
- Document the exact versions of Firebase SDKs used (Android BOM, iOS firebase-ios-sdk) in repo docs.
- Add a small smoke-check Gradle task that asserts `androidApp/google-services.json` exists and fail fast in CI if missing.

Next steps I can implement for you
- Add a CI Gradle task that runs schema/KSP checks and ensures `google-services.json` is present.
- Add a small `shared/src/androidMain` / `iosMain` `actual` initializer shim if you want to centralize `initializeFirebase()` in shared rather than in `AppDelegate`.
- Add sample code showing safe token retrieval (wait for `FCMToken` notification on iOS or use `FirebaseMessaging.getInstance().token` on Android).

