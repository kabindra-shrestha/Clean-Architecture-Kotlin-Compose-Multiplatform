# Dashboard Enhancements and Connectivity Fixes

This plan addresses three main areas: making the Navigation Rail scrollable for larger item sets, fixing the broken internet connectivity dialog logic, and providing a tool for API testing.

## User Review Required

> [!NOTE]
> The `.http` file is for use with the IntelliJ/Android Studio "HTTP Client" plugin. It allows you to run requests directly from the IDE.

## Proposed Changes

### Dashboard Navigation

#### [DashboardScreen.kt](file:///Users/vianet/Kabindra/Own/Clean-Architecture-Kotlin-Compose-Multiplatform/shared/src/commonMain/kotlin/com/kabindra/clean/architecture/presentation/ui/screen/splash/DashboardScreen.kt)

- **Scrollable Navigation Rail**: Wrap `navigationSuiteItems` content (internally managed by `NavigationSuiteScaffold`) or provide a scrollable container if possible. Since `NavigationSuiteScaffold` items are not directly scrollable via the DSL in current stable versions easily, I will implement a custom layout or ensure the rail part is scrollable.
- Actually, `NavigationSuiteScaffold` doesn't expose the Rail's scroll state directly. I will wrap the `NavigationSuiteScaffold` or use a custom `NavigationRail` inside the `content` if needed, but the best approach for `NavigationSuiteScaffold` is to ensure the items are placed in a scrollable column if the library supports it, or use a standard `PermanentNavigationDrawer` / `NavigationRail` if flexibility is needed.
- **Wait**, looking at `NavigationSuiteScaffold` source (mentally), it uses a standard `NavigationRail`. To make it scrollable, I might need to provide a custom `navigationSuiteItems` wrapper if the API allows, or switch to a manual `Scaffold` + `NavigationRail` implementation for better control.
- **Connectivity Dialog**: Fix the logic to properly react to `isConnected` state.

---

### Core Utilities

#### [Connectivity.kt](file:///Users/vianet/Kabindra/Own/Clean-Architecture-Kotlin-Compose-Multiplatform/shared/src/commonMain/kotlin/com/kabindra/clean/architecture/utils/Connectivity.kt)

- Fix `isConnectedState` which is currently hardcoded to `true`.
```diff
     override val isConnectedState: StateFlow<Boolean> =
         state.asStateFlow()
-            .map(scope) { /*it != NetworkConnection.NONE*/ true }
+            .map(scope) { it != NetworkConnection.NONE }
```

---

### API Testing

#### [NEW] [api_testing.http](file:///Users/vianet/Kabindra/Own/Clean-Architecture-Kotlin-Compose-Multiplatform/api_testing.http)

- Create an `.http` file with common endpoints, headers, and placeholders for tokens.
- Include `Authorization`, `User-Device`, `User-Device-Platform`, etc. headers.

---

## Verification Plan

### Manual Verification
- **Navigation Rail**: Add 20+ items temporarily to `DashboardCategory` and verify vertical scrolling works in Desktop/Tablet mode.
- **Connectivity**: Run the app on an emulator, disable Wi-Fi/Data, and verify the `GlobalErrorDialog` appears.
- **HTTP Client**: Open `api_testing.http` in Android Studio and run a sample request (e.g., `check-user`) to verify headers are correctly formatted.
