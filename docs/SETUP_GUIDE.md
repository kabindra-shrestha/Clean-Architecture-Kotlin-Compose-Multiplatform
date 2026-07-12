# Project Setup and Branding Guide

This guide covers how to set up the project and customize it for a new brand or package name.

## 1. Environment Configuration (BuildKonfig)

The project uses **BuildKonfig** for environment variables (API URLs, keys, etc.).

### How to change constants:
Edit `shared/build.gradle.kts`:
```kotlin
buildkonfig {
    defaultConfigs {
        buildConfigField(STRING, "BASE_URL", "https://your-api.com")
    }
    defaultConfigs("dev") {
        buildConfigField(STRING, "BASE_URL", "https://dev-api.your-api.com")
    }
}
```

### Running with a specific flavor:
- **Dev**: `./gradlew :androidApp:assembleDebug -Pbuildkonfig.flavor=dev`
- **Prod**: `./gradlew :androidApp:assembleDebug -Pbuildkonfig.flavor=prod`

## 2. Branding (Icons and Logo)

### Android Icons
1.  Generate your adaptive icon set.
2.  Replace files in `androidApp/src/main/res/mipmap-*`.
3.  Update the foreground color in `androidApp/src/main/res/values/colors.xml` and `values-night/colors.xml`.

### iOS Icons
1.  Open the project in Xcode.
2.  Navigate to `iosApp -> Assets.xcassets -> AppIcon`.
3.  Replace the placeholder images with your own.

### Web (Favicon)
1.  Replace the favicon in `webApp/src/wasmJsMain/resources/` or `jsMain/resources/`.

### In-App Logo (Shared)
- Replace `shared/src/commonMain/composeResources/drawable/splash_icon.xml` with your own vector logo.

## 3. Package Name Renaming

### Automatic (Recommended)
Use the **"Replace in Files"** (Ctrl+Shift+R) in Android Studio to replace `com.kabindra.clean.architecture` with your new package name across the whole project.

### Manual Verification
Ensure you check these files specifically:
- `androidApp/build.gradle.kts` (namespace and applicationId)
- `shared/build.gradle.kts` (namespace)
- `iosApp/iosApp.xcodeproj` (Bundle Identifier)
- All `package` declarations in Kotlin files.
- `AndroidManifest.xml` files.

## 4. Key Gradle Commands

| Goal | Command |
| :--- | :--- |
| Build Android | `./gradlew :androidApp:assembleDebug` |
| Run Web (Wasm) | `./gradlew :webApp:wasmJsBrowserDevelopmentRun` |
| Run Web (JS) | `./gradlew :webApp:jsBrowserDevelopmentRun` |
| Generate BuildKonfig | `./gradlew :shared:generateBuildKonfig` |
| Clean Project | `./gradlew clean` |
