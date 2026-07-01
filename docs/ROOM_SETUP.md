Room (Compose Multiplatform) — Setup & Troubleshooting

Overview
This project uses Room with Kotlin Multiplatform support (Room KSP) and exports schema files under `shared/schemas`. The shared `AppDatabase` is defined in `shared/src/commonMain` and platform-specific builders live in the Android/iOS modules (look for `getDatabaseBuilder()` in `composeApp` / `shared` platform source sets).

Common issues
- KSP not generating platform `actual` types (e.g. `AppDatabaseConstructor`) because KSP isn't configured for all targets.
- Schemas not generated or out-of-date.
- Wrong SQLite driver for a platform (use `BundledSQLiteDriver` for multiplatform/shared targets).
- Using `allowMainThreadQueries()` in production (remove it).

Validation and quick fixes
1. Check `shared/build.gradle.kts` for KSP and Room configuration. This template should contain lines similar to:
   - `alias(libs.plugins.ksp)`
   - `alias(libs.plugins.room)`
   - `room3 { schemaDirectory("$projectDir/schemas") }`
   - `dependencies { add("kspCommonMainMetadata", libs.room.compiler); add("kspAndroid", libs.room.compiler); add("kspIosArm64", libs.room.compiler); ... }`

2. Regenerate KSP outputs for each target (run these from project root):

```bash
# Android
./gradlew :shared:kspAndroid

# iOS (arm & simulator)
./gradlew :shared:kspIosArm64
./gradlew :shared:kspIosSimulatorArm64

# JS / Wasm
./gradlew :shared:kspJs
./gradlew :shared:kspWasmJs
```

3. Build the project so the Room-generated `actual` code is visible to Kotlin: `./gradlew :shared:assemble` (or `./gradlew build`). If KSP fails, inspect `build/reports` and the Gradle console for KSP errors.

4. Verify `shared/schemas/<fully-qualified-database-name>` contains JSON schema(s) after a successful run. If not present, ensure `schemaDirectory` path is correct and Room `exportSchema = true` in `@Database` annotation.

5. If `expect object AppDatabaseConstructor` is in commonMain but you do not see generated `actual` implementations, you can temporarily add small platform `actual` implementations that delegate to your platform builder. For example (Android actual):

```kotlin
// shared/src/androidMain/kotlin/.../AppDatabaseConstructor.android.kt
package com.kabindra.clean.architecture.data.source.room

import androidx.room3.RoomDatabaseConstructor

actual object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase =
        // call your existing platform getDatabaseBuilder or builder code
        com.kabindra.clean.architecture.db.getDatabaseBuilder()
}
```

Create matching `actual` objects for `iosMain`/`jsMain` if necessary. Prefer letting KSP generate them; platform manual `actual` implementations are a fallback.

6. Driver choice: prefer `BundledSQLiteDriver()` for shared/native contexts (it's already used in the project). Ensure platform `getDatabaseBuilder()` uses appropriate `name` or `context` parameters and does not produce unexpected file paths.

Production checklist
- Remove `.allowMainThreadQueries()` from builders.
- Use `RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING` for performance where appropriate.
- Commit `shared/schemas/*` to repository if you want deterministic schema tracking and migration support.
- Add CI tasks to run KSP for all targets and verify `shared/schemas` exists and is up-to-date.

If you want, I can:
- Add example `actual` `AppDatabaseConstructor` files for Android and iOS as a fallback.
- Add Gradle tasks that run KSP for all targets and fail the build when schema directory is missing or outdated.

